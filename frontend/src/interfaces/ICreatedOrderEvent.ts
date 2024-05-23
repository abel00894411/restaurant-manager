interface ICreatedOrderEvent extends CustomEvent {
    detail: {
        waiterId: number,
        waiter: string
    }
}

export default ICreatedOrderEvent;