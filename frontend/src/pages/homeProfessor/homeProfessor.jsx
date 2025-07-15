import { Outlet } from "react-router-dom";
import MenuLateralProfessor from "../../components/menuProfessor/menuProfessor";
import styles from "./homeProfessor.module.css";
import { ModuloResultados } from "../../components/compResultados/compResultados";
import { ModuloReceitas } from "../../components/compReceitas/compReceitas";
import { ModuloCadastroIngrediente } from "../../components/compCadastroIngredientes/compCadastroIngredientes";
import ModuloCadastroAluno from "../../components/compCadastroAluno/compCadastroAluno";
import { ModuloConfiguracaoDoJogo } from "../../components/compConfigJogo/compConfigJogo";


export function HomeProfessor() {

  return (
    <div className={styles.professorContainer}>
      <MenuLateralProfessor />

      <main className={styles.content}>
        <div className={styles.header}>
          <h1>Bem-vindo(a), Professor(a)</h1>


        </div>

        <Outlet />
        <ModuloConfiguracaoDoJogo />
        <ModuloCadastroAluno />
        <ModuloCadastroIngrediente />
        <ModuloReceitas />
        <ModuloResultados />


      </main>
    </div>
  );
}