import CadastroIngrediente from '../cadastroIngredientes/cadastroIngredientes';
import styles from './compCadastroIngredientes.module.css'
import { useState } from 'react';

export function ModuloCadastroIngrediente() {
 const [modalIngredienteAberto, setModalIngredienteAberto] = useState(false);

 const abrirModalIngrediente = () => setModalIngredienteAberto(true);
 const fecharModalIngrediente = () => setModalIngredienteAberto(false);

 return (
  <>
   <div className={styles.botoes}>
    <button className={styles.botaoCadastrar} onClick={abrirModalIngrediente}>
     Cadastrar Ingrediente
    </button>
   </div>

    <CadastroIngrediente isOpen={modalIngredienteAberto} onClose={fecharModalIngrediente} />
  </>
 );
}
