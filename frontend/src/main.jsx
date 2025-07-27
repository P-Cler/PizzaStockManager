import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import Login from './pages/login/login.jsx'
import RecuperarSenha from './pages/login/RecuperarSenha.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
    <Login />
  </StrictMode>
)
