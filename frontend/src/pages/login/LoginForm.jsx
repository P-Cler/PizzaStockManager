import React, { useState } from 'react'
import axios from 'axios'
import './login.css'

export default function LoginForm({tipoUsuario}) {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [mensagem, setMensagem] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const url = `https://dummyjson.com/auth/login`;

        try {
            const response = await axios.post(url, { email: email, password: senha,
            });

            setMensagem(`Login feito com sucesso! Token: ${response.data.token}`);

            localStorage.setItem('token', response.data.token);

            navigate(`/home/${tipoUsuario}`);

        } catch (err) {
            setMensagem('Falha no login' + err.response);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <label>Email</label>
            <input
                type="email"
                placeholder={`E-mail ${tipoUsuario}`}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
            />

            <label>Senha</label>
            <input
                type="password"
                placeholder="Digite sua senha"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
            />

            <button type="submit" className="botao-login">
                Entrar como {tipoUsuario}
            </button>


            {mensagem && <div className="mensagem">{mensagem}</div>}
        </form>
    )
}
