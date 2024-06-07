import fetchAPI from '../../util/fetchAPI';
import { useState } from 'react';
import devLog from '../../util/devLog';
import { menu } from '../../models/Menu';
import { Link } from 'react-router-dom';
import './InvoicePage.css';
import generateInvoicePDF from '../../util/generateInvoicePDF';

const currencyFormatter = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 2 });

const InvoicePage = () => {
    const [ formData, setFormData ] = useState({
        rfc: '',
        email: '',
        address: '',
        orderId: ''
    });

    const [ invoiceData, setInvoiceData ] = useState(undefined);

    const handleChange = (event) => {
        const input = event.target;

        setFormData(oldData => {
            return {
                ...formData,
                [input.name]: input.value
            };
        });
    };

    const handleGenerateClick = () => {
        const { rfc, email, address, orderId } = formData;

        if ( !rfc || !email || !address || !orderId ) {
            alert(`Por favor, llena todos los campos`);
            return;
        }

        fetchAPI('facturas', 'POST', {
            idOrden: orderId,
            correo: email,
            direccion: address,
            rfc: rfc
        })
        .then(res => {
            setInvoiceData(res.factura);
            generateInvoicePDF(res.factura);
        })
        .catch(error => {
            alert('No fue posible generar la factura');
            devLog(error.message);
        });
    };

    return (
        <>
            <div className="invoicePage__image-container">
                <Link to="/">
                    <img src="/img/logo.png" alt="Logotipo" />
                </Link>
            </div>

            <h1 className='invoicePage__title'>Genera tu factura</h1>

            <form className="invoicePage__form thin-border">
                <h4>Por favor, introduce tus datos</h4>

                <label>
                    RFC
                    <input type="text" name="rfc" onChange={handleChange} autoComplete='off' />
                </label>

                <label>
                    Correo electrónico
                    <input type="text" name="email" onChange={handleChange} autoComplete='off' />
                </label>

                <label>
                    Dirección de facturación
                    <input type="text" name="address" onChange={handleChange} autoComplete='off' />
                </label>

                <label>
                    Identificador de orden
                    <input type="text" name="orderId" onChange={handleChange} autoComplete='off' />
                </label>

                <button type="button" onClick={handleGenerateClick}>Generar factura</button>

                { invoiceData &&
                    <>
                        <h4>Orden #{invoiceData.orden.idOrden}</h4>

                        <table className='invoicePage__table'>
                            <thead>
                                <tr>
                                    <th>Ítems</th>
                                    <th>Precio unitario</th>
                                    <th>Cantidad</th>
                                    <th>Precio total</th>
                                </tr>
                            </thead>
                            <tbody>
                                { invoiceData.orden.items.map((item, i) => {
                                    const menuItem = menu.getItem(item.idItemMenu);
                                    const itemName = menuItem?.name;
                                    const itemPrice = menuItem?.price || 0;
                                    const quantity = item.cantidad;
                                    const totalItemPrice = itemPrice * quantity;

                                    return (
                                        <tr key={i}>
                                            <td>{itemName}</td>
                                            <td>{currencyFormatter.format(itemPrice)}</td>
                                            <td>{quantity}</td>
                                            <td>{currencyFormatter.format(totalItemPrice)}</td>
                                        </tr>
                                    )}
                                ) }
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colSpan={3} >Subtotal</td>
                                    <td>{ currencyFormatter.format(invoiceData.orden.subtotal) }</td>
                                </tr>
                                <tr>
                                    <td colSpan={3} >IVA (16%)</td>
                                    <td>{ currencyFormatter.format(invoiceData.orden.iva) }</td>
                                </tr>
                                <tr>
                                    <td colSpan={3} >Total</td>
                                    <td>{ currencyFormatter.format(invoiceData.total) }</td>
                                </tr>
                            </tfoot>
                        </table>
                        
                        <button type="button" onClick={() => generateInvoicePDF(invoiceData)}>Obtener PDF</button>
                    </>
                }
            </form>

        </>
        
    );
};

export default InvoicePage;
