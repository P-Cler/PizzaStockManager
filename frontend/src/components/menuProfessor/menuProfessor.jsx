import React from "react";
import { Link } from "react-router-dom";
import {
  Presentation,
  User,
  MonitorCog,
  BookMarked,
  ChartColumn,
  Pizza
} from "lucide-react";
import styles from "@/components/menuProfessor/menuProfessor.module.css";

export default function MenuLateralProfessor() {
  return (
    <aside className={styles.menuLateral}>
      <div className={styles.cabecalhoMenu}>
        <Presentation size={40} />
        <h3>Olá, Professor Carlos!</h3>
      </div>
      <nav className={styles.navegacao}>
        <ul>
          <li>
            <Link to="/professor/parametrizacao" className={styles.linkMenu}>
              <MonitorCog size={24} />
              <span>Configuração do jogo</span>
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
              <Pizza size={24} />
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
              <ChartColumn size={24} />
              <span>Resultados</span>
            </Link>
          </li>
        </ul>
      </nav>
    </aside>
  );
}