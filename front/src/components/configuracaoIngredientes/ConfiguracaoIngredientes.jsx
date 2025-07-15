import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import styles from './ConfiguracaoIngredientes.module.css'; 
import { Trash, PlusCircle } from 'lucide-react'; 


const API_URL = import.meta.env.VITE_API_URL;


const campoIdMap = {
  estoqueAtual: 1,
  estoqueMinimo: 2,
  estoqueMaximo: 3,
  leadTime: 4,
  pontoSeguranca: 5, 
};

export function ConfiguracaoIngredientes() {
  const { jogoId } = useParams(); 

  const [ingredientes, setIngredientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  
  useEffect(() => {
    if (!jogoId) {
      setLoading(false);
      setError("ID do jogo não encontrado na URL.");
      return;
    }

    const fetchIngredientes = async () => {
      try {
        setLoading(true);
        const response = await fetch(`${API_URL}/estoques/por-jogo/${jogoId}`);
        if (!response.ok) {
          throw new Error('Não foi possível carregar os ingredientes do estoque.');
        }
        const data = await response.json();
        console.log(data)
        console.log(data.itens);
        
        if (Array.isArray(data.itens)) {
            setIngredientes(data.itens);
        } else {
            throw new Error('O formato da resposta da API não é o esperado.');
        }

      } catch (err) {
        setError(err.message);
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchIngredientes();
  }, [jogoId]); 

  
  const handleChange = (index, fieldName, value) => {
    const novosIngredientes = [...ingredientes];
    novosIngredientes[index] = { ...novosIngredientes[index], [fieldName]: value };
    setIngredientes(novosIngredientes);
  };

  
  const handleBlur = async (ingredienteId, fieldName, value) => {
    const campoId = campoIdMap[fieldName];
    if (!campoId) {
      console.warn(`Mapeamento não encontrado para o campo: ${fieldName}`);
      return;
    }
    
    
    console.log(`Salvando... Jogo: ${jogoId}, Ingrediente: ${ingredienteId}, Campo: ${campoId}, Valor: ${value}`);

    try {
      const response = await fetch(`${API_URL}/estoques/jogo/${jogoId}/ingrediente/${ingredienteId}/campo/${campoId}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ valor: String(value) }), 
      });

      if (!response.ok) {
        
        const errorData = await response.json();
        throw new Error(errorData.message || `Falha ao atualizar o campo ${fieldName}.`);
      }
      
      console.log(`Campo ${fieldName} atualizado com sucesso!`);
      
      
    } catch (err) {
      console.error(`Erro ao salvar o campo ${fieldName}:`, err);
      alert(`Ocorreu um erro ao salvar o campo ${fieldName}. Verifique o console para mais detalhes.`);
      
      
    }
  };

  
  const handleAdicionar = () => {
    alert("Funcionalidade de adicionar novo ingrediente ainda não implementada.");
    
  };
  
  const handleDelete = (ingredienteId) => {
    alert(`Funcionalidade de deletar o ingrediente ${ingredienteId} ainda não implementada.`);
    
  };

  if (loading) {
    return <div className={styles.centered}>Carregando ingredientes...</div>;
  }

  if (error) {
    return <div className={`${styles.centered} ${styles.error}`}>Erro: {error}</div>;
  }

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Cadastro de Ingredientes</h2>
      <div className={styles.tableWrapper}>
        <table className={styles.table}>
          <thead className={styles.thead}>
            <tr>
              <th>Ingrediente</th>
              <th>Unid.</th>
              <th>Estoque Atual</th>
              <th>Estoque Mínimo</th>
              <th>Estoque Máximo</th>
              <th>Lead Time</th>
              <th>Ponto de Seg.</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {ingredientes.map((ing, index) => (
              <tr key={ing.id}>
                <td>{ing.nomeIngrediente}</td>
                <td>{ing.unidadeMedida || ""}</td>
                <td>
                  <input
                    type="number"
                    name="estoqueAtual"
                    value={ing.estoqueAtual}
                    onChange={(e) => handleChange(index, 'estoqueAtual', e.target.value)}
                    onBlur={(e) => handleBlur(ing.ingredienteId, 'estoqueAtual', e.target.value)}
                    className={styles.input}
                  />
                </td>
                <td>
                  <input
                    type="number"
                    name="estoqueMinimo"
                    value={ing.estoqueMinimo}
                    onChange={(e) => handleChange(index, 'estoqueMinimo', e.target.value)}
                    onBlur={(e) => handleBlur(ing.ingredienteId, 'estoqueMinimo', e.target.value)}
                    className={styles.input}
                  />
                </td>
                <td>
                   <input
                    type="number"
                    name="estoqueMaximo"
                    value={ing.estoqueMax}
                    onChange={(e) => handleChange(index, 'estoqueMaximo', e.target.value)}
                    onBlur={(e) => handleBlur(ing.ingredienteId, 'estoqueMaximo', e.target.value)}
                    className={styles.input}
                  />
                </td>
                <td>
                   <input
                    type="text" 
                    name="leadTime"
                    value={ing.leadTime}
                    onChange={(e) => handleChange(index, 'leadTime', e.target.value)}
                    onBlur={(e) => handleBlur(ing.ingredienteId, 'leadTime', e.target.value)}
                    className={styles.input}
                  />
                </td>
                <td>
                   <input
                    type="number"
                    name="pontoSeguranca"
                    value={ing.pontoPedido}
                    onChange={(e) => handleChange(index, 'pontoSeguranca', e.target.value)}
                    onBlur={(e) => handleBlur(ing.ingredienteId, 'pontoSeguranca', e.target.value)}
                    className={styles.input}
                  />
                </td>
                <td className={styles.actions}>
                  <button onClick={() => handleDelete(ing.ingredienteId)} className={styles.deleteButton}>
                    <Trash />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className={styles.addButtonContainer}>
        <button onClick={handleAdicionar} className={styles.addButton}>
          <PlusCircle /> Adicionar
        </button>
      </div>
    </div>
  );
}