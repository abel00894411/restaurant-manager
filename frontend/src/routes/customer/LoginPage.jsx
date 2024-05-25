import React from 'react';
import './Login.css'; 

function Login() {
  return (
    <div className="login">
      <header className="login-header">
        <h1>[ LOGO ]</h1>
      </header>
      <div>
        <h2>¡Bienvenido a nuestro restaurante!</h2>
        <div className="login-box">
          <h3>Empleado, inicia sesión</h3>
          <form>
            <label>
              RFC
              <input type="text" name="rfc" />
            </label>
            <label>
              Contraseña
              <input type="password" name="password" />
            </label>
            <button type="submit">Continuar</button>
          </form>
        </div>
        <div className="clientes">
          <a href="/facturas">Genera tus facturas &gt;</a>
          <a href="/menu">Mira nuestro menú &gt;</a>
        </div>
      </div>
    </div>
  );
}

export default Login;
