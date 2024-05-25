import React from 'react';
import { Link } from 'react-router-dom';
import './Login.css'; 

function Login() {
  return (
	<div className="login">
	  <header className="login-header">
		<img src="/img/logo.png" alt="Logotipo" />
	  </header>
	  <div className='login-main'>
		<h2>¡Bienvenido a nuestro restaurante!</h2>
		<div className="login-box">
		  <h4>Empleado, inicia sesión</h4>
		  <form>
			<label>
			  RFC
			  <input type="text" name="rfc" />
			</label>
			<label>
			  Contraseña
			  <input type="password" name="password" />
			</label>
			<button type="button">Continuar</button>
		  </form>
		</div>
		<div className="clientes">
            <h4 className='clientes__titulo'>Clientes</h4>
			<Link to="/facturas">Genera tus facturas &gt;</Link>
			<Link to="/menu">Mira nuestro menú &gt;</Link>
		</div>
	  </div>
	</div>
  );
}

export default Login;
