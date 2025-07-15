import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import MenuLateralProfessor from "../../components/menuProfessor/menuProfessor";
import CadastroIngrediente from "../../components/cadastroIngredientes/cadastroIngredientes";
import styles from "./homeProfessor.module.css";
import TabelaCadastroAluno from "../../components/compCadastroAluno/compCadastroAluno";
import { ModuloResultados } from "../../components/compResultados/compResultados";
import { ModuloReceitas } from "../../components/compReceitas/compReceitas";
import { ModuloCadastroIngrediente } from "../../components/compCadastroIngredientes.jsx/compCadastroIngredientes";
import ModuloCadastroAluno from "../../components/compCadastroAluno/compCadastroAluno";

export function HomeProfessor() {
  const [modalIngredienteAberto, setModalIngredienteAberto] = useState(false);


  const abrirModalIngrediente = () => setModalIngredienteAberto(true);
  const fecharModalIngrediente = () => setModalIngredienteAberto(false);

  return (
    <div className={styles.professorContainer}>
      <MenuLateralProfessor />

      <main className={styles.content}>
        <div className={styles.header}>
          <h1>Bem-vindo(a), Professor(a)</h1>


        </div>

        <Outlet />
        <ModuloCadastroAluno />
        <ModuloCadastroIngrediente />
        <ModuloReceitas />
        <ModuloResultados />
        <CadastroIngrediente isOpen={modalIngredienteAberto} onClose={fecharModalIngrediente} />

      </main>
    </div>
  );
}