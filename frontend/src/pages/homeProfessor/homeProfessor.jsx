import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import MenuLateralProfessor from "../../components/menuProfessor/menuProfessor";
import CadastroIngrediente from "../../components/cadastroIngredientes/cadastroIngredientes";
import CadastroAluno from "../../components/CadastroAluno/CadastroAluno"; 

import styles from "./homeProfessor.module.css";

export function HomeProfessor() {
  const [modalIngredienteAberto, setModalIngredienteAberto] = useState(false);
  const [modalAlunoAberto, setModalAlunoAberto] = useState(false);

  const abrirModalIngrediente = () => setModalIngredienteAberto(true);
  const fecharModalIngrediente = () => setModalIngredienteAberto(false);

  const abrirModalAluno = () => setModalAlunoAberto(true);
  const fecharModalAluno = () => setModalAlunoAberto(false);

  return (
    <div className={styles.professorContainer}>
      <MenuLateralProfessor />

      <main className={styles.content}>
        <div className={styles.header}>
          <h1>Bem-vindo(a), Professor(a)</h1>
          <div className={styles.botoes}>
            <button className={styles.botaoCadastrar} onClick={abrirModalIngrediente}>
              Cadastrar Ingrediente
            </button>
            <button className={styles.botaoCadastrar} onClick={abrirModalAluno}>
              Cadastrar Aluno
            </button>
          </div>
        </div>

        <Outlet />

        
        <CadastroIngrediente isOpen={modalIngredienteAberto} onClose={fecharModalIngrediente} />
        <CadastroAluno isOpen={modalAlunoAberto} onClose={fecharModalAluno} />
      </main>
    </div>
  );
}
