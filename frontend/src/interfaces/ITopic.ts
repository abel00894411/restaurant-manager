import IEventFunction from './IEventFunction';

interface ITopic {
    path: string,
    event?: IEventFunction | undefined
}

export default ITopic;