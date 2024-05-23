import Stomp from 'stompjs';
import Order from './Order';
import OrderItem from './OrderItem';
import devLog from '../util/devLog';
import ITopic from '../interfaces/ITopic';

/**
 * Abstract class for singleton manager classes that will take care of tasks,
 * data, events and server connections related to the objects assigned to the
 * employee user.
 */
abstract class JobManager {
    static #instance: JobManager | undefined = undefined;
    protected list: Order[] | OrderItem[] = []; // official list of objects the app works with
    #client: Stomp.Client;
    readonly #messageHeaders = {
        'Authorization': localStorage.getItem('token')
    }

    constructor(topics: ITopic[] = []) {
        if (JobManager.#instance) {
            return JobManager.#instance;
        }
        
        JobManager.#instance = this;

        const webSocketURL = import.meta.env.VITE_WEBSOCKET_URL;

        if (!webSocketURL) {
            throw new Error(`Unable to instanciate JobManager, env variable VITE_WEBSOCKET_URL is not set`);
        }

        const token = localStorage.getItem('token');
        
        if (!token) {
            throw new Error(`Unable to instanciate JobManager, there's no token`);
        }
        
        const headers = {
            'Authorization': `Bearer ${token}`
        };
        
        this.#client = Stomp.client(webSocketURL);
        this.#client.debug = () => {};

        this.#client.connect(headers,
            frame => {
                devLog('Connection to WebSocket successfull');
                devLog(frame);

                if (!topics || topics.length == 0) {
                    console.log(topics);
                    devLog('No topics to subscribe');
                }

                // Subscribe to all topics
                for (const topic of topics) {
                    let callback: (message: Stomp.Message) => any;
                    
                    if (topic.event) {
                        callback = (message: Stomp.Message) => {
                            const customEvent = topic.event?.(message);
                            
                            if (customEvent) {
                                document.dispatchEvent(customEvent);
                            }
                        }
                    } else {
                        callback = (message: Stomp.Message) => {
                            console.log(`Message from ${topic.path}: ${message.body}`);
                        }
                    }
                        
                    const sub = this.#client.subscribe(
                        topic.path,
                        callback,
                        { ...headers, id: Math.random() } // unique id for each subscription
                    );

                    devLog(`Suscribed to ${topic.path} with id ${sub.id}`);
                }

            },

            error => {
                devLog(`Error with WebSocket connection: ${error}`);
                alert('La conexión se ha cerrado por un error');
            }
        );

    }

    /**
     * Method returns an array containing a copy of each item in the manager's
     * list.
     */
    getAll(): Order[] | OrderItem[] {
        return this.list.map(item => {
            if (item instanceof Order) {
                return new Order(item.id, item.date, item.state);
            } else if (item instanceof OrderItem) {
                return new OrderItem(item.id, item.orderId, item.menuItemId, item.state, item.quantity, item.creationDateTime);
            } else {
                return Object.assign({}, item);
            }
        });
    }

    /**
     * Send a STOMP message to the server
     * @param destination The destination for the message
     * @param body The body of the message as an object
     */
    sendMessage(destination: string, body: object = {}): boolean {
        const headers = this.#messageHeaders;

        if (!this.#client.connected) {
            return false;
        }

        if (!headers.Authorization) {
            devLog(`Sent a STOMP message to ${destination} with "${headers.Authorization}" as the Authorization header`);
        }

        this.#client.send(destination, this.#messageHeaders, JSON.stringify(body));
        return true;
    }

    /**
     * Method will receive an Order or OrderItem as argument. Based on the given
     * object's id or the lack of it, it will ask the server to either update an
     * existing object or create a new one.
     * Later, and not as part of this method, the server response will trigger
     * the corresponding changes in local data.
     * For OrderItem: When creating a new order item, all of its properties 
     * other than id and creationDateTime must be defined.
     * For Order: When creating a new order, its list must include at least one
     * OrderItem.
     * @param object The object
     * @returns true on success, false otherwise
     */
    abstract set(object: Order | OrderItem): boolean

    /**
     * Method will find and return Order/OrderItem data, or undefined if none.
     * The method returns a copy of the local Order/OrderItem object, not a
     * reference to the original.
     * @param id The id of the desired object
     * @param type The class the desired object is an instance of
     */
    abstract get(id: number, type: Function): Order | OrderItem | undefined;

}

export default JobManager;
