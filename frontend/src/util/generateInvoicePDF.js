import { jsPDF } from "jspdf";
import { menu } from '../models/Menu';

const currencyFormatter = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 2 });

const generateInvoicePDF = (invoiceData) => {
    const domInvoice = document.createElement('html');

    const domInvoiceHead = document.createElement('head');
    const domInvoiceCustomCSS = document.createElement('style');
    domInvoiceCustomCSS.innerHTML = (
        `.invoice, .invoice * {
          font-family: arial !important;
          font-size: 5px !important;
        }`
    );

    domInvoiceHead.appendChild(domInvoiceCustomCSS);

    const domInvoiceBody = document.createElement('body');
    domInvoiceBody.classList.add('invoice');
    domInvoiceBody.innerHTML += 
    (
        `<h1>Orden&nbsp;#${invoiceData.orden.idOrden}</h1>

        <br>

        Para:
        <br>
        ${invoiceData.rfc}
        ${invoiceData.correo}
        ${invoiceData.direccion}

        <br><br>

        <table className='invoicePage__table'>
            <thead>
                <tr>
                    <th>Ítems</th>
                    <th>Precio unitario</th>
                    <th>Cantidad</th>
                    <th>Precio total</th>
                </tr>
            </thead>
            <tbody class='invoicePage__table__body'>
            </tbody>
            <tfoot class='invoicePage__table__foot'>
                <tr>
                    <td colSpan=3>Subtotal</td>
                    <td>${ currencyFormatter.format(invoiceData.orden.subtotal) }</td>
                </tr>
                <tr>
                    <td colSpan=3>IVA (16%)</td>
                    <td>${ currencyFormatter.format(invoiceData.orden.iva) }</td>
                </tr>
                <tr>
                    <td colSpan=3>Total</td>
                    <td>${ currencyFormatter.format(invoiceData.total) }</td>
                </tr>
            </tfoot>
        </table>`
    );

    const domInvoiceTableBody = domInvoiceBody.querySelector('.invoicePage__table__body');
    
    for (const item of invoiceData.orden.items) {
        const menuItem = menu.getItem(item.idItemMenu);
        const itemName = menuItem?.name;
        const itemPrice = menuItem?.price || 0;
        const quantity = item.cantidad;
        const totalItemPrice = itemPrice * quantity;
    
        domInvoiceTableBody.innerHTML += (`
            <tr>
                <td>${itemName}</td>
                <td>${currencyFormatter.format(itemPrice)}</td>
                <td>${quantity}</td>
                <td>${currencyFormatter.format(totalItemPrice)}</td>
            </tr>
        `)   
    }

    domInvoice.appendChild(domInvoiceHead);
    domInvoice.appendChild(domInvoiceBody);

    const pdf = new jsPDF();
    pdf.html(domInvoice, {
        callback: doc => {
                doc.save(`Factura orden #${invoiceData.orden.idOrden}`, { 
                    returnPromise: true,
            });
        },
        autoPaging: 'text',
        margin: 8
    });
};

export default generateInvoicePDF;