import React from "react";
// import styles from "./menuProfessor.module.css";
import {
  ChalkboardIcon,
  PersonIcon,
  Chartbar,
  Bookbookmark,
  Calendarstar,
  BreadIcon,
} from "@phosphor-icons/react";

export function MenuLateralProfessor({ onMenuClick }) {
  return (
    <aside className="menuLateral">
      <ChalkboardIcon size={40} />
      <h3>Olá, Professor Roberto!</h3>
      <nav>
        <ul>
          <Chartbar size={30} />
          <li onClick={() => onMenuClick("config")}>Parametrização do jogo</li>
          <PersonIcon size={30} />
          <li onClick={() => onMenuClick("alunos")}>Cadastro de Alunos</li>
          <BreadIcon size={30} />
          <li onClick={() => onMenuClick("ingredientes")}>Cadastro de Ingredientes</li>
          <Bookbookmark size={30} />
          <li onClick={() => onMenuClick("receita")}>Receita do Produto</li>
          <Calendarstar size={30} />
          <li onClick={() => onMenuClick("resultados")}>Resultados</li>
        </ul>
      </nav>
    </aside>
  );
}
