import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom'; 
import styles from '@/components/moduloConfiguracaoDoJogo/moduloConfiguracaoDoJogo.module.css';
import { ModuloAlunosAssociados } from '../moduloAlunosAssociados/moduloAlunosAssociados';
import { ConfiguracaoIngredientes } from '../configuracaoIngredientes/ConfiguracaoIngredientes';
import { PlayIcon } from 'lucide-react'; 

const API_URL = import.meta.env.VITE_API_URL;

const campoIdMap = {
  tempoTotal: 1,
  numCiclos: 2,
  maxProdutos: 3,
  minProdutos: 4,
};

export function ModuloConfiguracaoDoJogo({ receitas }) {
  const { jogoId } = useParams();
  const navigate = useNavigate(); 

  const [loading, setLoading] = useState(true);
  const [isStarting, setIsStarting] = useState(false); 
  const [error, setError] = useState(null);

  const [form, setForm] = useState({
    tempoTotal: '',
    cicloPedido: '',
    numCiclos: '',
    maxProdutos: '',
    receita: '',
    minProdutos: '',
  });

  useEffect(() => {
    const fetchJogo = async () => {
      try {
        setLoading(true);
        const response = await fetch(`${API_URL}/jogos/${jogoId}`);
        if (!response.ok) {
          throw new Error('Não foi possível carregar os dados do jogo.');
        }
        const dadosJogo = await response.json();

        setForm({
          tempoTotal: dadosJogo.tempoTotal || '',
          cicloPedido: dadosJogo.tempoTotal && dadosJogo.numeroCiclos ? dadosJogo.tempoTotal / dadosJogo.numeroCiclos : '',
          numCiclos: dadosJogo.numeroCiclos || '',
          maxProdutos: dadosJogo.maximoPizzas || '',
          minProdutos: dadosJogo.minimoPizzas || '',
          receita: dadosJogo.receitaId || '',
        });
      } catch (err) {
        setError(err.message);
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    if (jogoId) {
      fetchJogo();
    }
  }, [jogoId]);

  function handleChange(e) {
  const { name, value } = e.target;

  setForm((prev) => ({ ...prev, [name]: value }));

  atualizarCampoNoBanco(name, value);
}

async function atualizarCampoNoBanco(fieldName, value) {
  const campoId = campoIdMap[fieldName];
  if (!campoId) {
    console.warn(`Mapeamento não encontrado para o campo: ${fieldName}`);
    return;
  }

  const valorEnviado = ['tempoTotal', 'numCiclos', 'maxProdutos', 'minProdutos'].includes(fieldName)
    ? parseInt(value, 10)
    : value;

  try {
    const response = await fetch(`${API_URL}/jogos/${jogoId}/campo/${campoId}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ valor: valorEnviado }),
    });

    if (!response.ok) {
      console.error(`Erro ao atualizar campo "${fieldName}". Status: ${response.status}`);
    } else {
      console.log(`Campo "${fieldName}" atualizado com sucesso no banco.`);
    }
  } catch (error) {
    console.error(`Erro ao atualizar campo "${fieldName}":`, error);
  }
}

  async function handleSubmit(e) {
    e.preventDefault();
    setLoading(true);

    const updatePromises = Object.entries(form)
      .filter(([, value]) => value !== '')
      .map(([fieldName, value]) => {
        const campoId = campoIdMap[fieldName];
        if (!campoId) {
          console.warn(`Mapeamento não encontrado para o campo: ${fieldName}`);
          return Promise.resolve();
        }

        return fetch(`${API_URL}/jogos/${jogoId}/campo/${campoId}`, {
          method: 'PATCH',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ valor: String(value) }),
        });
      });

    try {
      const responses = await Promise.all(updatePromises);
      for (const res of responses) {
        if (res && !res.ok) {
          throw new Error(`Falha ao atualizar um campo. Status: ${res.status}`);
        }
      }
      alert('Jogo configurado com sucesso!');
    } catch (error) {
      console.error('Erro ao salvar a configuração do jogo:', error);
      alert('Ocorreu um erro ao salvar. Verifique os dados e tente novamente.');
    } finally {
      setLoading(false);
    }
  }

  
const handleIniciarJogo = async () => {
    if (!window.confirm("Tem certeza que deseja iniciar o jogo? Após iniciado, as configurações não poderão ser alteradas.")) {
      return;
    }
    setIsStarting(true);
    try {
      const response = await fetch(`${API_URL}/jogos/${jogoId}/iniciar`, {
        method: 'POST',
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({ message: "Não foi possível iniciar o jogo." }));
        throw new Error(errorData.message);
      }
      

      localStorage.setItem('jogoAtivoId', jogoId);
      
      window.dispatchEvent(new Event('jogoIniciado'));
      
      alert('Jogo iniciado com sucesso! Você será redirecionado para o painel do jogo.');
      navigate(`/jogo/${jogoId}/painel`);

    } catch (err) {
      console.error("Erro ao iniciar o jogo:", err);
      alert(`Erro ao iniciar o jogo: ${err.message}`);
    } finally {
      setIsStarting(false);
    }
};


  if (error) {
    return <div className={styles.error}>Erro: {error}</div>;
  }

  return (
    
    <div className={styles.pageContainer}>
      <form
        className={styles['config-jogo-form']}
        aria-labelledby="config-jogo-titulo"
        onSubmit={handleSubmit}
      >
        <fieldset disabled={loading || isStarting}>
          <legend id="config-jogo-titulo">
            Configuração do Jogo (ID: {jogoId})
          </legend>
          
            <div className={styles['form-row']}>
                <label htmlFor="tempoTotal">Tempo total do jogo (min):</label>
                <input id="tempoTotal" name="tempoTotal" type="number" value={form.tempoTotal} onChange={handleChange} required />
            </div>
            <div className={styles['form-row']}>
                <label htmlFor="numCiclos">Número de ciclos (pedidos):</label>
                <input id="numCiclos" name="numCiclos" type="number" value={form.numCiclos} onChange={handleChange} min="1" required />
            </div>
            <div className={styles['form-row']}>
                <label htmlFor="cicloPedido">Tempo por ciclo (min):</label>
                <input id="cicloPedido" name="cicloPedido" type="text" value={form.cicloPedido} disabled placeholder="Automático" />
            </div>
            <div className={styles['form-row']}>
                <label htmlFor="minProdutos">Mínimo de pizzas por pedido:</label>
                <input id="minProdutos" name="minProdutos" type="number" value={form.minProdutos} onChange={handleChange} min="1" required />
            </div>
            <div className={styles['form-row']}>
                <label htmlFor="maxProdutos">Máximo de pizzas por pedido:</label>
                <input id="maxProdutos" name="maxProdutos" type="number" value={form.maxProdutos} onChange={handleChange} min="1" required />
            </div>
            <div className={styles['form-row']}>
                <label htmlFor="receita">Sabor da Pizza:</label>
                <select id="receita" name="receita" value={form.receita} onChange={handleChange} disabled>
                    <option value="calabresa">Pizza de Calabresa</option>
                </select>
            </div>
          
          <button type="submit" disabled={loading || isStarting} className={styles['submit-button']}>
            {loading ? 'Salvando...' : 'Salvar Configuração'}
          </button>
        </fieldset>
      </form>

      {jogoId && <ModuloAlunosAssociados jogoId={jogoId} />}
      {jogoId && <ConfiguracaoIngredientes jogoId={jogoId} />}

      {/* Seção final com o botão de iniciar */}
      <div className={styles.finalActionsContainer}>
        <button
          onClick={handleIniciarJogo}
          className={styles.iniciarButton}
          disabled={loading || isStarting}
        >
          <PlayIcon />
          {isStarting ? 'Iniciando...' : 'Iniciar Jogo'}
        </button>
      </div>
    </div>
  );
}