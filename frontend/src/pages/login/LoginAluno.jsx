import React from 'react'

export default function LoginAluno() {
    return (


        <form>
            <label>Email</label>
            <input type='email' placeholder="Digite seu e-mail" />

            <label>Senha</label>
            <input type='password' placeholder="Digite sua senha" />

            <button type="submit" className='botao-login' >Entrar</button>

            <div className="recuperar-senha">
                <a href="#">Esqueceu sua senha?</a>
            </div>
        </form>
    )
}
