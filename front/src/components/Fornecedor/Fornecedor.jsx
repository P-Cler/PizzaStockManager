import React, { useState, useEffect, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import styles from './Fornecedor.module.css';
import { Truck, CheckCircle, XCircle, Package } from 'lucide-react';

const API_URL = import.meta.env.VITE_API_URL;

export default function Fornecedor({ refreshKey, cicloAtual, onPedidoRecebido }) { 
    const { jogoId } = useParams();
    const [entregas, setEntregas] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchEntregas = useCallback(async () => {
        if (!jogoId) return;
        setLoading(true);
        try {
            const response = await fetch(`${API_URL}/entregas/por-jogo/${jogoId}`);
            if (!response.ok) throw new Error('Erro ao buscar as entregas.');
            const data = await response.json();
            setEntregas(data);
        } catch (err) {
            setError(err.message);
            console.error(err);
        } finally {
            setLoading(false);
        }
    }, [jogoId]);

    useEffect(() => {
        fetchEntregas();
    }, [fetchEntregas, refreshKey]);

    const handleAction = async (action, entregaId) => {
        const endpoints = {
            receber: `${API_URL}/entregas/${entregaId}/receber`,
            cancelar: `${API_URL}/entregas/${entregaId}/cancelar`,
        };

        if (action === 'cancelar' && !window.confirm("Tem certeza que deseja cancelar esta entrega?")) return;

        try {
            const response = await fetch(endpoints[action], { method: 'POST' });
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `Falha ao ${action} entrega.`);
            }
            fetchEntregas(); 
            if(onPedidoRecebido){
                onPedidoRecebido();
            }
        } catch (err) {
            console.error(`Erro ao ${action} entrega:`, err);
            alert(err.message);
        }
    };
    
    
    const getStatusInfo = (status) => {
        const statusMap = {
            PENDENTE: { text: "Pendente", className: styles.statusPendente },
            A_CAMINHO: { text: "A Caminho", className: styles.statusEmTransito },
            DISPONIVEL_PARA_RECEBIMENTO: { text: "Pronto para Receber", className: styles.statusDisponivelRecebimento },
            ENTREGUE: { text: "Entregue", className: styles.statusEntregue },
            CANCELADA: { text: "Cancelada", className: styles.statusCancelado },
        };
        return statusMap[status] || { text: status.replace('_', ' '), className: styles.statusDefault };
    };

    if (loading) return <p className={styles.loading}>Carregando entregas dos fornecedores...</p>;
    if (error) return <p className={styles.error}>Erro: {error}</p>;

    return (
        <div className={styles.container}>
            <h2 className={styles.title}><Truck size={28}/> Fornecedores</h2>
            <div className={styles.grid}>
                {entregas.length === 0 && <p className={styles.noData}>Nenhuma entrega solicitada para este jogo.</p>}
                {entregas.map((item) => {
                    const statusInfo = getStatusInfo(item.status);
                    const ciclosRestantes = item.cicloDisponivel - (cicloAtual || 0);
                    return (
                        <div key={item.id} className={styles.card}>
                            <div className={styles.cardHeader}>
                                <h3 className={styles.cardTitle}><Package size={20}/> {item.nomeIngrediente}</h3>
                                <div 
                                    className={`${styles.statusIndicator} ${statusInfo.className}`}
                                    title={`Status: ${statusInfo.text}`}
                                ></div>
                            </div>

                            <div className={styles.detailsGrid}>
                                <div>Quantidade: <strong>{item.quantidadePedida}</strong></div>
                                <div>Pedido no Ciclo: <strong>{item.cicloDoPedido}</strong></div>
                                <div>Disponível no Ciclo: <strong>{item.cicloDisponivel}</strong></div>
                                <div>Recebido no Ciclo: <strong>{item.cicloDoRecebimento || '–'}</strong></div>
                                <div>Ciclos Restantes: <strong>{ciclosRestantes > 0 ? ciclosRestantes : 0}</strong></div>
                            </div>

                            <div className={styles.cardActions}>
                                {item.status === 'DISPONIVEL_PARA_RECEBIMENTO' && (
                                    <button className={styles.buttonReceber} onClick={() => handleAction('receber', item.id)}>
                                        <CheckCircle size={16}/> Receber
                                    </button>
                                )}
                                {(item.status === 'PENDENTE' || item.status === 'A_CAMINHO') && (
                                     <button className={styles.buttonCancelar} onClick={() => handleAction('cancelar', item.id)}>
                                        <XCircle size={16}/> Cancelar
                                    </button>
                                )}
                               {(item.status === 'ENTREGUE' || item.status === 'CANCELADA') && (
                                    <p className={`${styles.statusText} ${statusInfo.className}`}>{statusInfo.text}</p>
                               )}
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}