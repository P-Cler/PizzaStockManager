import React from 'react';
import { Outlet } from 'react-router-dom';
import MenuLateralProfessor from '../../components/menuProfessor/menuProfessor';

import styles from "./homeProfessor.module.css";

export function HomeProfessor() {
  return (
    <div className={styles.professorContainer}>
      <MenuLateralProfessor />

      <main className={styles.content}>

        <Outlet />
      </main>
    </div>
  );
}