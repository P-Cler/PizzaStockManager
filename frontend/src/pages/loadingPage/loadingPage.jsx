import pizzaStockManagerLogo from '../../assets/pizzaStockManagerLogo.png';
import './loadingPage.module.css';
import React, { useEffect, useState } from 'react';

export function LoadingPage() {
  const [progress, setProgress] = useState(0);
  const loadingTime = 3000; // tempo total de loading em milissegundos (3 segundos)
  const intervalTime = 30; // intervalo de atualização da barra

  useEffect(() => {
    let current = 0;
    const interval = setInterval(() => {
      current += intervalTime;
      setProgress(Math.min((current / loadingTime) * 100, 100));
      if (current >= loadingTime) clearInterval(interval);
    }, intervalTime);

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="loading-container">
      <img
        src={pizzaStockManagerLogo}
        alt="Logo do Jogo Pizza Stock Manager"
        className="loading-image"
      />
      <div className="loading-bar-background">
        <div
          className="loading-bar-fill"
          style={{ width: `${progress}%` }}
        ></div>
      </div>
    </div>
  );
}