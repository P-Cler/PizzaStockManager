import React, { useState } from 'react'
import './login.module.css'
import './login.css'

export default function Login() {
    const [tipoUsuario, setTipoUsuario] = useState('aluno');


  return (
    <div className="login-container">
        <div className="login-box">

            <div className="login-aba">
                <button className={ tipoUsuario === 'aluno' ? 'active' : " "} 
                onClick={() => setTipoUsuario('aluno')}>Aluno</button>

                <button className={ tipoUsuario === 'professor' ? 'active' : " "} 
                onClick={() => setTipoProfessor('professor')}>Professor</button>
            </div>

            <h2>LOGIN</h2>

            <form>
                <label>Email</label>
                <input type='email' id="email" placeholder="Digite seu e-mail"/>

                <label>Senha</label>
                <input type='senha' id="senha" placeholder="Digite sua senha"/>

                <button type="submit" className='botao-login' >Entrar</button>

                <div className="recuperar-senha">
                    <a href="#">Esqueceu sua senha?</a>
                </div>
            </form>
        </div>
    </div>
  )
}
