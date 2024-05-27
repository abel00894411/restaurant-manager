import React from 'react';
import fetchAPI from '../../util/fetchAPI';
import { useState } from 'react';
import devLog from '../../util/devLog';
import { menu } from '../../models/Menu';
import './InvoicePage.css';

const currencyFormatter = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 });

const InvoicePage = () => {
    const [invoiceData, setInvoiceData] = useState({
        idOrden: '',
        correo: '',
        direccion: '',
        rfc: ''
    });

    const [orderData, setOrderData] = useState(undefined);

    const submitInvoice = (invoiceData) => {
        
        fetchAPI('/facturas', 'POST', invoiceData)
            .then(res => {
                
                setOrderData(res.factura.orden);

            })
            .catch(error => {
                alert(`No fue posible generar la factura`);
                devLog(error.message);
            });
    };

    const handleChange = (event) => {
        const { target } = event;

        setInvoiceData(old => {
            return {
                ...old,
                [target.name]: target.value
            };
        });
    };

    const generateInvoice = (orderData) => {
        let res = '<h1>Factura</h1>';

        for (const field in orderData) {
            const ffield = `${field[0].toUpperCase()}${field.slice(1).toUpperCase()}`;
            if (field == 'items') {
                for (const item of (orderData.items)) {
                    res += `<p>${menu.getItem(item.idItemMenu).name}</p>`;
                    res += `<ol class='invoice-item'>`;
                        res += `<li>Precio unitario: ${currencyFormatter.format(menu.getItem(item.idItemMenu).price)}</li>`;
                        res += `<li>Precio total: ${currencyFormatter.format(menu.getItem(item.idItemMenu).price*item.cantidad)}</li>`;
                    res += `</ol>`;
                }

                continue;
            }

            res += `<p>${ffield}: ${orderData[field]}</p>`;
        }

        document.body.innerHTML = res;
        print();
    };

    return (
        <div className="invoice-form-container">
            <header className="invoice-form-header">
                <img src="/img/logo.png" alt="Logotipo" />
            </header>
            <div className='form-main'>
                <h2>Genera tu factura</h2>
                <div className="form-box">
                    <h3>Por favor, introduce tus datos</h3>
                    <form className='invoice-form'>
                        <label>
                            RFC
                            <input autoComplete='off' type="text" name="rfc" value={invoiceData.rfc} onChange={handleChange} />
                        </label>
                        <label>
                            Correo electrónico
                            <input autoComplete='off' type="email" name="correo" value={invoiceData.correo} onChange={handleChange} />
                        </label>
                        <label>
                            Dirección de facturación
                            <input autoComplete='off' type="text" name="direccion" value={invoiceData.direccion} onChange={handleChange} />
                            
                        </label>
                        <label>
                            Identificador de orden
                            <input autoComplete='off' type="text" name="idOrden" value={invoiceData.idOrden} onChange={handleChange} />
                        </label>
                        <button type="button" onClick={()=>submitInvoice(invoiceData)}>Buscar orden</button>
                    </form>

                    { orderData && 
                        <>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Items</th>
                                        <th>Precio unitario</th>
                                        <th>Cantidad</th>
                                        <th>Precio total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    { orderData.items.map((item, i) => {
                                        return (
                                            <tr key={i}>
                                                <td>{item.producto}</td>
                                                <td>{currencyFormatter.format(menu.getItem(item.idItemMenu).price)}</td>
                                                <td>{item.cantidad}</td>
                                                <td>{currencyFormatter.format(menu.getItem(item.idItemMenu).price*item.cantidad)}</td>
                                            </tr>
                                        );
                                    }) }
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td></td><td></td>
                                        <td>Subtotal</td>
                                        <td>{orderData.subtotal}</td>
                                    </tr>
                                    <tr>
                                        <td></td><td></td>
                                        <td>IVA (16%)</td>
                                        <td>{orderData.iva}</td>
                                    </tr>
                                    <tr>
                                        <td></td><td></td>
                                        <td>Total</td>
                                        <td>{orderData.total}</td>
                                    </tr>
                                </tfoot>
                            </table>

                            <button onClick={generateInvoice(orderData)}>Generar factura</button>
                        </>
                    }
                </div>
            </div>
        </div>
    );
};

export default InvoicePage;
