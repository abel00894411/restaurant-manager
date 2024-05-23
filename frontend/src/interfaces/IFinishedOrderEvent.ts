interface IFinishedOrderEvent extends CustomEvent {
    detail: {
        orderId: number,
        subtotal: number,
        vat: number,
        total: number
    }
}

export default IFinishedOrderEvent;