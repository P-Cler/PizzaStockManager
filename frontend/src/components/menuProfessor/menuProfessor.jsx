import React from "react";
import { Link } from "react-router-dom";
import {
  Presentation,
  User,
  BarChart,
  BookMarked,
  CalendarDays,
  Sandwich
} from "lucide-react";
import styles from "@/components/menuProfessor/menuProfessor.module.css";

export default function MenuLateralProfessor() {
  return (
    <aside className={styles.menuLateral}>
      <div className={styles.cabecalhoMenu}>
        <Presentation size={40} />
        <h3>Olá, Professor Roberto!</h3>
      </div>
      <nav className={styles.navegacao}>
        <ul>
          <li>
            <Link to="/professor/parametrizacao" className={styles.linkMenu}>
              <BarChart size={24} />
              <span>Parametrização do jogo</span>
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