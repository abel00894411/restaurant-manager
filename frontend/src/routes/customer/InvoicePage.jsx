import React from 'react';
import './InvoicePage.css';

const InvoicePage = () => {
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
                            <input type="text" name="rfc" />
                        </label>
                        <label>
                            Correo electrónico
                            <input type="email" name="email" />
                        </label>
                        <label>
                            Dirección de facturación
                            <input type="text" name="billingAddress" />
                            
                        </label>
                        <label>
                            Identificador de orden
                            <input type="text" name="orderId" defaultValue="20240326230" />
                        </label>
                        <button type="button">Buscar orden</button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default InvoicePage;
