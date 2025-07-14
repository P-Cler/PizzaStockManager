// src/components/pedidos/PedidosGamificados.jsx

import React, { useState, useEffect, useCallback } from 'react';
import { Pause, Play } from "lucide-react";
import { useParams } from "react-router-dom";
import styles from './Pedidos.module.css';

const API_URL = import.meta.env.VITE_API_URL;

export default function PedidosGamificados({ refreshKey, onCycleEnd }) {
    const { jogoId } = useParams();

    const [pedidos, setPedidos] = useState([]);
    const [cicloInfo, setCicloInfo] = useState({ duracao: 0, cicloAtual: 0 });
    const [tempoRestante, setTempoRestante] = useState(0);
    const [isPaused, setIsPaused] = useState(false);
    const [loading, setLoading] = useState(true);

    // MUDANÇA 1: Função de busca unificada e simplificada
    const fetchDados = useCallback(async () => {
        if (!jogoId) return;
        setLoading(true);
        try {
            const response = await fetch(`${API_URL}/jogos/${jogoId}`);
            if (!response.ok) throw new Error('Erro ao buscar dados do jogo.');
            const jogo = await response.json();
            
            setPedidos(jogo.pedidos || []);
            setTempoRestante(jogo.tempoRestanteCicloSegundos);
            setCicloInfo({ duracao: jogo.duracaoCiclos * 60, cicloAtual: jogo.cicloAtual }); // Convertendo minutos para segundos
            setIsPaused(jogo.status === 'PAUSADO');

        } catch (error) {
            console.error("Erro ao carregar dados dos pedidos:", error);
        } finally {
            setLoading(false);
        }
    }, [jogoId]);

    // MUDANÇA 2: useEffect principal que reage à refreshKey
    useEffect(() => {
        fetchDados();
    }, [fetchDados, refreshKey]);

    // MUDANÇA 3: useEffect para o cronômetro, agora muito mais simples
    useEffect(() => {
        // Para o cronômetro se o tempo acabou, estiver pausado ou carregando.
        if (tempoRestante <= 0 || isPaused || loading) {
            // Se o tempo acabou de chegar a zero, notifica o pai.
            if (tempoRestante === 0 && !loading) {
                onCycleEnd();
            }
            return;
        }

        // A cada segundo, diminui o tempo restante.
        const timerId = setInterval(() => {
            setTempoRestante(prev => prev - 1);
        }, 1000);

        return () => clearInterval(timerId);

    }, [tempoRestante, isPaused, loading, onCycleEnd]);

    const produzirPedido = async (id) => {
        try {
            await fetch(`${API_URL}/pedidos/${id}/produzir`, { method: 'PATCH' });
            onCycleEnd(); // Força a atualização de tudo
        } catch (error) {
            console.error("Erro ao produzir pedido:", error);
        }
    };

    const rejeitarPedido = async (id) => {
        try {
            await fetch(`${API_URL}/pedidos/${id}/rejeitar`, { method: 'PATCH' });
            onCycleEnd(); // Força a atualização de tudo
        } catch (error) {
            console.error("Erro ao rejeitar pedido:", error);
        }
    };

    const formatarTempo = (segundos) => {
        if (segundos < 0) segundos = 0;
        const minutos = Math.floor(segundos / 60);
        const secs = segundos % 60;
        return `${String(minutos).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
    };

    if (loading) {
        return <div className={styles.container}>Carregando Pedidos...</div>;
    }

    return (
        <div className={styles.container}>
            <div className={styles.headerPedidos}>
                <h2 className={styles.titulo}>🍕 Pedidos do Ciclo {cicloInfo.cicloAtual}</h2>
                {/* Aqui poderiam entrar os botões de pause/play se necessário */}
            </div>
            <div className={styles.grid}>
                {pedidos.length > 0 ? pedidos.map((pedido) => (
                    <div key={pedido.id} className={styles.card}>
                        <div className={styles.cardHeader}>
                           <h3 className={styles.cardTitle}>
                                Pedido <span className={styles.codigoPedido}>({pedido.codigoPedido})</span>
                           </h3>
                        </div>
                        <div className={styles.cardText}>
                           Quantidade: <strong>{pedido.quantidadePizzas} Pizzas</strong>
                        </div>
                        <div className={styles.progressContainer}>
                            <div className={styles.progressBar}>
                                <div
                                    className={styles.progressFill}
                                    style={{ width: `${(tempoRestante / cicloInfo.duracao) * 100}%` }}
                                />
                            </div>
                            <span className={styles.timer}>{formatarTempo(tempoRestante)}</span>
                        </div>
                        {pedido.status === "PENDENTE" ? (
                            <div className={styles.buttonGroup}>
                                <button className={styles.produzirButton} onClick={() => produzirPedido(pedido.id)}>
                                    Produzir
                                </button>
                                <button className={styles.rejeitarButton} onClick={() => rejeitarPedido(pedido.id)}>
                                    Rejeitar
                                </button>
                            </div>
                        ) : (
                            <div className={`${styles.statusDisplay} ${styles[`status${pedido.status}`]}`}>
                                Status: {pedido.status}
                            </div>
                        )}
                    </div>
                )) : <p>Nenhum pedido para o ciclo atual.</p>}
            </div>
        </div>
    );
}