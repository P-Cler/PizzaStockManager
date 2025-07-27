import React, { useState } from 'react'
import LoginForm from './LoginForm';
import './login.css'
import RecuperarSenha from './RecuperarSenha';


export default function Login() {
    const [tipoUsuario, setTipoUsuario] = useState('aluno');
    const [recuperarSenha, setRecuperarSenha] = useState(false);

    return (
        <div className="login-container">
            <div className="login-box">
                {!recuperarSenha ? (
                    <>
                <div className='abas'>
                    <button className={tipoUsuario === 'aluno' ? 'aba active' : 'aba'} onClick={() => setTipoUsuario('aluno')}>Aluno</button>

                    <button className={tipoUsuario === 'professor' ? 'aba active' : 'aba'} onClick={() => setTipoUsuario('professor')}>Professor</button>
                </div>

                <h2>LOGIN</h2>

                        <LoginForm tipoUsuario={tipoUsuario} />
                        <div className='recuperar-senha'>
                               <a href="#" onClick={() => setRecuperarSenha(true)}>Esqueceu sua senha?</a>
                            </div>
                    </>
                ) : (
                    <>
                        <RecuperarSenha />
                        <button className='voltar-login' type='button' onClick={() => setRecuperarSenha(false)}>Voltar para Login</button>
                    </>
                )}
            </div>
        </div>
    )
}
