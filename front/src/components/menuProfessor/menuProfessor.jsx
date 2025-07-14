import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import {
  Presentation,
  User,
  BarChart,
  BookMarked,
  CalendarDays,
  Sandwich,
  PlusCircle, 
  Gamepad2
} from 'lucide-react';
import styles from '@/components/menuProfessor/menuProfessor.module.css';
import { useState, useEffect } from 'react';

const API_URL = import.meta.env.VITE_API_URL;

export default function MenuLateralProfessor() {
  const navigate = useNavigate();
    const [jogoAtivoId, setJogoAtivoId] = useState(null); 


  const handleCriarJogo = async () => {
    try {
      const response = await fetch(`${API_URL}/jogos`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      body: JSON.stringify({
        tempoTotal: 10,
        minimoPizzas: 20,
        maximoPizzas: 100,
        numeroCiclos: 5,
      }),
      });

      if (!response.ok) {
        throw new Error('Falha ao criar o jogo.');
      }

      const novoJogo = await response.json();
      const jogoId = novoJogo.id; 

      navigate(`/professor/parametrizacao/${jogoId}`);
    } catch (error) {
      console.error('Erro ao criar o jogo:', error);
      alert('Não foi possível criar o jogo. Tente novamente.');
    }
  };

    useEffect(() => {
    
    const verificarJogoAtivo = () => {
      const id = localStorage.getItem('jogoAtivoId');
      setJogoAtivoId(id);
    };

    verificarJogoAtivo(); 

    
    window.addEventListener('jogoIniciado', verificarJogoAtivo);
    
    
    window.addEventListener('storage', verificarJogoAtivo);

    
    return () => {
      window.removeEventListener('jogoIniciado', verificarJogoAtivo);
      window.removeEventListener('storage', verificarJogoAtivo);
    };
  }, []);

  return (
    <aside className={styles.menuLateral}>
      <div className={styles.cabecalhoMenu}>
        <Presentation size={40} />
        <h3>Olá, Professor Roberto!</h3>
      </div>
      <nav className={styles.navegacao}>
        <ul>
          <li>
            <button onClick={handleCriarJogo} className={styles.linkMenu}>
              <PlusCircle size={24} />
              <span>Criar Novo Jogo</span>
            </button>
          </li>
          <li>
              <Link to={`/jogo/${jogoAtivoId}/painel`} className={`${styles.linkMenu} ${styles.jogoAtivo}`}>
                <Gamepad2 size={24} />
                <span>Jogo em Andamento</span>
              </Link>
            </li>
          <li>
            <Link to="/professor/alunos" className={styles.linkMenu}>
              <User size={24} />
              <span>Cadastro de Alunos</span>
            </Link>
          </li>
          <li>
            <Link to="/professor/ingredientes" className={styles.linkMenu}>
              <Sandwich size={24} />
              <span>Cadastro de Ingredientes</span>
            </Link>
          </li>
          <li>
            <Link to="/professor/receita" className={styles.linkMenu}>
              <BookMarked size={24} />
              <span>Receita do Produto</span>
            </Link>
          </li>
          <li>
            <Link to="/professor/resultados" className={styles.linkMenu}>
              <CalendarDays size={24} />
              <span>Resultados</span>
            </Link>
          </li>
        </ul>
      </nav>
    </aside>
  );
}