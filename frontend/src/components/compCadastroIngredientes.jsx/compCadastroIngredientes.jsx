import styles from './compCadastroIngredientes.module.css';
import { useState } from 'react';

export function ModuloCadastroIngrediente() {

const [abrirModal, setAbrirModal] = useState(false)

 return (
  <>
   <div className={styles.botoes}>
    <button className={styles.botaoCadastrar} onClick={ () => setAbrirModal(true)}>
     Cadastrar Ingrediente
    </button>
   </div>
  </>
 );
}
