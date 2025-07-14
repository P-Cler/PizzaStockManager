import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import MenuLateralProfessor from "../../components/menuProfessor/menuProfessor";
import CadastroIngrediente from "../../components/cadastroIngredientes/cadastroIngredientes";
<<<<<<< HEAD
<<<<<<< HEAD
import CadastroAluno from "../../components/CadastroAluno/CadastroAluno";
=======
import CadastroAluno from "../../components/CadastroAluno/CadastroAluno"; 
>>>>>>> 32b1d04a0b6ca05f54ddc9a8ece885478e47f29a

=======
>>>>>>> 73752e619b8257c1fe24ae09b221c7effd141f06
import styles from "./homeProfessor.module.css";
import TabelaCadastroAluno from "../../components/compCadastroAluno/compCadastroAluno";
import { ModuloResultados } from "../../components/compResultados/compResultados";
import { ModuloReceitas } from "../../components/compReceitas/compReceitas";
import { ModuloCadastroIngrediente } from "../../components/compCadastroIngredientes.jsx/compCadastroIngredientes";

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
<<<<<<< HEAD

<<<<<<< HEAD

=======
        
>>>>>>> 32b1d04a0b6ca05f54ddc9a8ece885478e47f29a
=======
        <ModuloCadastroAluno />
        <ModuloCadastroIngrediente />
        <ModuloReceitas />
        <ModuloResultados />
>>>>>>> 73752e619b8257c1fe24ae09b221c7effd141f06
        <CadastroIngrediente isOpen={modalIngredienteAberto} onClose={fecharModalIngrediente} />

      </main>
    </div>
  );
}