// import { MenuLateral, MenuLateralProfessor } from "../../components/menuProfessor/menuProfessor";
import styles from "./homeProfessor.module.css"
import CadastroAluno from '../../components/cadastroAluno/cadastroAluno';
import { useState}  from "react";

export function HomeProfessor() {
const [modalOpen, setModalOpen] = useState(false);

  return (
    <div className={styles.homeProfessor}>
        {/* <MenuLateralProfessor /> */}
        <button onClick={() => setModalOpen(true)}>+ Adicionar Aluno</button>
      <CadastroAluno isOpen={modalOpen} onClose={() => setModalOpen(false)} />
    </div>
  );
}