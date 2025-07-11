import React, { useState } from 'react';
import axios from 'axios';
import './login.css';

export default function RecuperarSenha() {
    const [email, setEmail] = useState("");
    const [mensagem, setMensagem] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

    try {
        await axios.post(``, { email });
        alert('Email de recuperação enviado!')
        setMensagem('');

    } catch (err) {
        setMensagem('Erro ao Enviar. Verifique o e-mail digitado!')
    }
}

  return (
    <div className='senha-container'>
        <div className="senha-box">
            <h2>Recuperar Senha</h2>
            <form onSubmit={handleSubmit}>
                <label>Email Cadastrado:</label>
                <input 
                type='email' placeholder='Digite seu e-mail' value={email} onChange={(e) => setEmail(e.target.value)} required/>
                <button type='submit' className='botao-login'>Recuperar Senha</button>
            </form>

            {mensagem && <div className='mensagem'>{mensagem}</div>}

            
        </div>
    </div>
  )
}
