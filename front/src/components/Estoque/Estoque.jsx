import React, { useState, useEffect, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import styles from './Estoque.module.css';
import { ShoppingCart, PackageCheck, X, Plus, Minus } from 'lucide-react';

const API_URL = import.meta.env.VITE_API_URL;

function ModalPedido({ isOpen, onClose, ingrediente, onFazerPedido }) {
    const [quantidade, setQuantidade] = useState(10);

    useEffect(() => {
        if (ingrediente) {
            setQuantidade(ingrediente.quantidade || 10);
        }
    }, [ingrediente]);

    if (!isOpen || !ingrediente) return null;

    const ajustarQuantidade = (valor) => {
        setQuantidade((prev) => Math.max(0, prev + valor));
    };
    
    const handleSubmit = () => {
        onFazerPedido(ingrediente.ingredienteId, quantidade);
        onClose();
    };

    return (
        <div className={styles.modalOverlay} onClick={onClose}>
            <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
                <div className={styles.modalHeader}>
                    <h2>Fazer Pedido: {ingrediente.nomeIngrediente}</h2>
                    <button onClick={onClose} className={styles.closeButton}><X size={24} /></button>
                </div>
                <div className={styles.modalBody}>
                    <p>Defina a quantidade que deseja pedir.</p>
                    <div className={styles.quantityControl}>
                        <button onClick={() => ajustarQuantidade(-10)}><Minus /> 10</button>
                        <button onClick={() => ajustarQuantidade(-1)}><Minus /></button>
                        <input
                            type="number"
                            value={quantidade}
                            onChange={(e) => setQuantidade(Number(e.target.value))}
                            className={styles.quantityInput}
                        />
                        <button onClick={() => ajustarQuantidade(1)}><Plus /></button>
                        <button onClick={() => ajustarQuantidade(10)}><Plus /> 10</button>
                    </div>
                </div>
                <div className={styles.modalFooter}>
                    <button onClick={handleSubmit} className={styles.confirmButton}>Confirmar Pedido</button>
                </div>
            </div>
        </div>
    );
}



export default function EstoqueGamificado({refreshKey}) {
    const { jogoId } = useParams();
    const [ingredientes, setIngredientes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [ingredienteSelecionado, setIngredienteSelecionado] = useState(null);

    const fetchEstoque = useCallback(async () => {
        if (!jogoId) return;
        setLoading(true);
        try {
            const response = await fetch(`${API_URL}/estoques/${jogoId}`);
            if (!response.ok) throw new Error('Erro ao buscar o estoque do jogo.');
            const data = await response.json();
            setIngredientes(data);
            console.log(data);
            
        } catch (error) {
            setError(error.message);
            console.error(error);
        } finally {
            setLoading(false);
        }
    }, [jogoId]);

    useEffect(() => {
        fetchEstoque();
    }, [fetchEstoque], refreshKey);

    const getCorEstoque = (estoqueAtual, estoqueMin) => {
        const percentual = (estoqueAtual / estoqueMin) * 100;
        if (percentual <= 100) return styles.estoqueCritico; 
        if (percentual <= 150) return styles.estoqueAlerta; 
        return styles.estoqueNormal;
    };

    const abrirModal = (ingrediente) => {
        setIngredienteSelecionado(ingrediente);
        setIsModalOpen(true);
    };

    const fecharModal = () => {
        setIsModalOpen(false);
        setIngredienteSelecionado(null);
    };

const fazerPedido = async (ingredienteId, quantidade) => {
    try {
        const response = await fetch(`${API_URL}/entregas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                estoqueId: ingredienteSelecionado.id, // ID do estoque
                quantidade: Number(quantidade)        // Envia como decimal
            }),
        });

        if (!response.ok) {
            throw new Error('Erro ao fazer o pedido de entrega.');
        }

        const data = await response.json();
        console.log("Entrega solicitada:", data);
        alert(`Pedido de ${quantidade} unidades de ${ingredienteSelecionado?.nomeIngrediente} realizado com sucesso!`);
        fetchEstoque(); // Atualiza o estoque após o pedido
    } catch (error) {
        console.error("Erro ao fazer pedido:", error);
        alert("Erro ao fazer o pedido.");
    }
};


    
    const receberPedido = (ingrediente) => {
        alert(`Funcionalidade "Receber" para ${ingrediente.nomeIngrediente} não implementada.`);
    }

    if (loading) return <p className={styles.loading}>Carregando estoque...</p>;
    if (error) return <p className={styles.error}>Erro: {error}</p>;

    return (
        <div className={styles.container}>
            <h2 className={styles.title}>🧂 Estoque de Ingredientes</h2>
            <div className={styles.grid}>
                {ingredientes.map((item) => (
                    <div key={item.id} className={styles.card}>
                        <h3 className={styles.cardTitle}>{item.nomeIngrediente}</h3>
                        <div className={styles.detailsGrid}>
                            <div>Estoque Atual:{" "}
                                <span className={getCorEstoque(item.estoqueAtual, item.estoqueMinimo)}>
                                    {item.estoqueAtual}
                                </span>
                            </div>
                            <div>Estoque Mínimo: {item.estoqueMinimo}</div>
                            <div>Estoque Máximo: {item.estoqueMax}</div>
                            <div>Lead Time: {item.leadTime} dia(s)</div>
                            <div>Ponto de Reposição: {item.pontoSeguranca}</div>
                        </div>
                        <div className={styles.cardActions}>
                            <button className={styles.buttonPedir} onClick={() => abrirModal(item)}>
                                <ShoppingCart size={16} /> Pedir
                            </button>
                            <button className={styles.buttonSecondary} onClick={() => receberPedido(item)}>
                                <PackageCheck size={16}/> Receber
                            </button>
                        </div>
                    </div>
                ))}
            </div>
            
            <ModalPedido
                isOpen={isModalOpen}
                onClose={fecharModal}
                ingrediente={ingredienteSelecionado}
                onFazerPedido={fazerPedido}
            />
        </div>
    );
}