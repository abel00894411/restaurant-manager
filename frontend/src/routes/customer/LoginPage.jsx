import { useState } from 'react';
import { Link } from 'react-router-dom';
import fetchAPI from '../../util/fetchAPI';
import './Login.css'; 
import devLog from '../../util/devLog';

function Login() {
  const [formData, setFormData] = useState({ rfc: '', password: '' });

  const handleChange = (event) => {
    const { target } = event;

    setFormData(oldData => {
        return {
            ...oldData,
            [target.name]: target.value
        }
    });

  };

  const submit = () => {
    fetchAPI('login', 'POST', formData)
      .then(res => {
        sessionStorage.setItem('token', res.token);
        location.assign('/');
      })
      .catch(error => {
        alert('Inicio de sesión incorrecto, vuelve a intentarlo');
        devLog(error.message);
      });
  };

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
              <input autoComplete="off" type="text" name="rfc" value={formData.rfc} onChange={handleChange}/>
            </label>
            <label>
              Contraseña
              <input autoComplete="off" type="password" name="password" value={formData.password} onChange={handleChange}/>
            </label>
            <button type="button" onClick={submit}>Continuar</button>
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
