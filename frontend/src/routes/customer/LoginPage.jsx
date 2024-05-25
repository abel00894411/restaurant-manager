import React from 'react';
import './Login.css'; 

function Login() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>[ LOGO ]</h1>
      </header>
      <main>
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
      </main>
    </div>
  );
}

export default Login;
