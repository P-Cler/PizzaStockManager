<<<<<<< HEAD
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
=======
import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import './index.css';
import { BrowserRouter } from 'react-router-dom';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
    <App />
    </BrowserRouter>
  </React.StrictMode>
);
>>>>>>> 8ff2a64c3a506bc8c193bfd8a304c51c366171b9
