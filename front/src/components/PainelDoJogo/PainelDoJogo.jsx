import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import styles from './PainelDoJogo.module.css';
import { Clock, Recycle, Trophy, Info, Hourglass } from 'lucide-react'; 


import PedidosGamificados from '../Pedidos/Pedidos';
import EstoqueGamificado from '../Estoque/Estoque';
import Fornecedor from '../Fornecedor/Fornecedor';

const API_URL = import.meta.env.VITE_API_URL;

export function PainelDoJogo() {
  const { jogoId } = useParams();
  const navigate = useNavigate();

  const [jogo, setJogo] = useState(null);
  const [tempoRestanteTotal, setTempoRestanteTotal] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [refreshKey, setRefreshKey] = useState(Date.now());

  
  const handleFinalizarJogo = async () => {
    console.log("Tempo do jogo esgotado. Finalizando...");
    try {
      await fetch(`${API_URL}/jogos/${jogoId}/finalizar`, { method: 'POST' });
      alert("O jogo foi finalizado! Você será redirecionado para a página de resultados.");
      navigate(`/professor/resultados/${jogoId}`);
    } catch (err) {
      console.error("Erro ao finalizar o jogo:", err);
      alert("Não foi possível finalizar o jogo automaticamente.");
    }
  };

  
  useEffect(() => {
    const fetchDadosDoJogo = async () => {
      setLoading(true);
      try {
        const response = await fetch(`${API_URL}/jogos/${jogoId}`);
        if (!response.ok) throw new Error('Jogo não encontrado ou falha ao carregar.');
        
        const data = await response.json();
        
        
        setJogo(data);
        
        
        
        if (data.tempoRestanteTotalSegundos !== undefined) {
            setTempoRestanteTotal(data.tempoRestanteTotalSegundos);
        } else {
            
            console.warn("O campo 'tempoRestanteTotalSegundos' não foi encontrado na resposta da API. O timer geral pode não funcionar corretamente.");
        }

      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchDadosDoJogo();
  }, [jogoId, refreshKey]); 

  
  useEffect(() => {
    if (tempoRestanteTotal <= 0 || jogo?.status !== 'EM_ANDAMENTO') {
        if (tempoRestanteTotal === 0 && jogo?.status === 'EM_ANDAMENTO') {
            handleFinalizarJogo();
        }
        return;
    }

    const timerId = setInterval(() => {
      setTempoRestanteTotal(prev => prev > 0 ? prev - 1 : 0);
    }, 1000);

    return () => clearInterval(timerId);
  }, [tempoRestanteTotal, jogo?.status]); 

  
  const handleCycleEnd = () => {
    console.log("Fim do ciclo detectado! Atualizando todos os componentes...");
    setRefreshKey(Date.now());
  };

  const formatarTempo = (segundos) => {
      if (segundos < 0) return "00:00";
      const min = Math.floor(segundos / 60);
      const seg = segundos % 60;
      return `${String(min).padStart(2, '0')}:${String(seg).padStart(2, '0')}`;
  };

  if (loading) return <div className={styles.centered}>Carregando painel do jogo...</div>;
  if (error) return <div className={`${styles.centered} ${styles.error}`}>{error}</div>;

  
  if (!jogo) {
    return <div className={styles.centered}>Nenhum dado do jogo para exibir.</div>;
  }

  return (
    <main className={styles.painelContainer}>
      <header className={styles.header}>
        <h1>Painel do Jogo</h1>
        <div className={styles.status}>
          <span className={`${styles.statusIndicator} ${styles[jogo.status?.toLowerCase()]}`}></span>
          {jogo.status?.replace('_', ' ') || 'STATUS INDEFINIDO'}
        </div>
      </header>

      <div className={styles.widgetsGrid}>
        <div className={styles.widget}>
          <Clock className={styles.widgetIcon} size={40} />
          <div className={styles.widgetContent}>
            <h3>Tempo Total Restante</h3>
            <p className={styles.widgetValue}>{formatarTempo(tempoRestanteTotal)}</p>
          </div>
        </div>
        <div className={styles.widget}>
          <Recycle className={styles.widgetIcon} size={40} />
          <div className={styles.widgetContent}>
            <h3>Ciclo Atual</h3>
            <p className={styles.widgetValue}>{jogo.cicloAtual} / {jogo.numeroCiclos}</p>
          </div>
        </div>
        <div className={styles.widget}>
          <Trophy className={styles.widgetIcon} size={40} />
          <div className={styles.widgetContent}>
            <h3>Pontuação</h3>
            <p className={styles.widgetValue}>{jogo.pontuacaoTotal || '0'}</p>
          </div>
        </div>
      </div>

      <div className={styles.gameComponents}>
        <PedidosGamificados refreshKey={refreshKey} onCycleEnd={handleCycleEnd} />
        <EstoqueGamificado refreshKey={refreshKey} />
        <Fornecedor refreshKey={refreshKey} />
      </div>
      
      <section className={styles.logSection}>
          <h2><Info size={20}/> Log de Eventos do Jogo</h2>
          <div className={styles.logBox}>
            {jogo.pedidos && jogo.pedidos.length > 0 ? (
                
                [...jogo.pedidos].sort((a, b) => new Date(b.dataPedido) - new Date(a.dataPedido)).map(p => (
                    <p key={p.id}>
                        <span>{new Date(p.dataPedido).toLocaleTimeString('pt-BR')}</span>
                         - Pedido ({p.codigoPedido}) foi <strong>{p.status.toLowerCase()}</strong>.
                    </p>
                ))
            ) : (
                <p><span>--:--:--</span> - Aguardando início do primeiro ciclo...</p>
            )}
          </div>
      </section>
    </main>
  );
}