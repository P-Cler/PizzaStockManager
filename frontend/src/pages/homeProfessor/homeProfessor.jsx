import { MenuLateral, MenuLateralProfessor } from "../../components/menuProfessor/menuProfessor";
import styles from "./homeProfessor.module.css"

export function HomeProfessor() {

  return (
    <>
    <div className={styles.homeProfessor}>
        <MenuLateralProfessor />
    </div>
    </>
  )

}