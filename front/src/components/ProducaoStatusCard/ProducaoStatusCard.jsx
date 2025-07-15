import React, { useEffect, useState, useCallback } from 'react';
import { Package } from 'lucide-react';
import styles from './ProducaoStatusCard.module.css';
import { useParams } from 'react-router-dom';

const API_URL = import.meta.env.VITE_API_URL;

export default function ProducaoStatusCard({ refreshKey }) {
    const { jogoId } = useParams();
    const [quantidadeProduzida, setQuantidadeProduzida] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchQuantidadeProduzida = useCallback(async () => {
        if (!jogoId) return;
        setLoading(true);
        try {
            const response = await fetch(`${API_URL}/estoques/por-jogo/${jogoId}`);
            if (!response.ok) throw new Error('Erro ao buscar dados de produção.');
            const data = await response.json();
            setQuantidadeProduzida(data.quantidadeProduzidaTotal || 0);
        } catch (err) {
            setError(err.message);
            console.error(err);
        } finally {
            setLoading(false);
        }
    }, [jogoId]);

    useEffect(() => {
        fetchQuantidadeProduzida();
    }, [fetchQuantidadeProduzida, refreshKey]);

    if (loading) {
        return <div className={styles.card}>Carregando produção...</div>;
    }

    if (error) {
        return <div className={`${styles.card} ${styles.error}`}>Erro: {error}</div>;
    }

    return (
        <div className={styles.card}>
            <Package className={styles.icon} size={40} />
            <div className={styles.content}>
                <h3>Produção Total</h3>
                <p>{quantidadeProduzida} unidades</p>
            </div>
        </div>
    );
}
