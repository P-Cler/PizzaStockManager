import React, { useState, useEffect } from 'react';
import styles from './modalBuscaAluno.module.css';

const API_URL = import.meta.env.VITE_API_URL;

export function ModalBuscaAluno({ jogoId, onClose, onAssociateSuccess }) {
  const [searchTerm, setSearchTerm] = useState('');
  const [allAlunos, setAllAlunos] = useState([]);
  const [filteredAlunos, setFilteredAlunos] = useState([]);
  const [alunoSelecionado, setAlunoSelecionado] = useState(null);
  const [loading, setLoading] = useState(true);
  const [associating, setAssociating] = useState(false);
  const [error, setError] = useState('');

  
  useEffect(() => {
  const fetchAllAlunos = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`${API_URL}/alunos`);
      if (!response.ok) {
        throw new Error('Falha ao carregar la lista de alunos do sistema.');
      }
      const data = await response.json();

      const normalized = data.map(aluno => ({
        ...aluno,
        nome: aluno.nome?.trim() || '000',
        email: aluno.email?.trim() || '000',
        matricula: aluno.matricula || '000',
      }));

      setAllAlunos(normalized);
      setFilteredAlunos(normalized);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  fetchAllAlunos();
}, []);

  
  useEffect(() => {
    if (!searchTerm) {
      setFilteredAlunos(allAlunos);
      return;
    }
    const lowercasedFilter = searchTerm.toLowerCase();
    
    
    const filtered = allAlunos.filter(aluno => {
        const nome = aluno.nome || '00';
        const email = aluno.email || '00';
        const matricula = String(aluno.matricula || '00');

        return nome.toLowerCase().includes(lowercasedFilter) ||
               email.toLowerCase().includes(lowercasedFilter) ||
               matricula.toLowerCase().includes(lowercasedFilter);
    });

    setFilteredAlunos(filtered);
  }, [searchTerm, allAlunos]);

  
  const handleAssociate = async () => {
    if (!alunoSelecionado) return;
    setAssociating(true);
    setError('');
    try {
      const response = await fetch(`${API_URL}/jogos-alunos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          jogoId: Number(jogoId),
          alunoId: alunoSelecionado.id,
        }),
      });

      const responseText = await response.text();
      if (!response.ok) {
        throw new Error(responseText || 'Falha ao associar aluno.');
      }
      
      alert(responseText);
      onAssociateSuccess();

    } catch (err) {
      setError(err.message);
      alert(`Erro: ${err.message}`);
    } finally {
      setAssociating(false);
    }
  };

  return (
    <div className={styles.modalBackdrop} onClick={onClose}>
      <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
        <div className={styles.modalHeader}>
          <h2>Buscar e Adicionar Aluno</h2>
          <button onClick={onClose} className={styles.closeButton}>&times;</button>
        </div>
        
        <div className={styles.searchForm}>
          <input
            type="text"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            placeholder="Filtre por nome, matrícula ou email..."
            className={styles.searchInput}
          />
        </div>

        {error && <p className={styles.errorText}>{error}</p>}

        {loading ? (
          <p>Carregando lista de alunos...</p>
        ) : (
          <div className={styles.alunoListContainer}>
            <ul className={styles.alunoList}>
              {filteredAlunos.length > 0 ? (
                filteredAlunos.map(aluno => (
                  <li 
                    key={aluno.id} 
                    className={`${styles.alunoListItem} ${alunoSelecionado?.id === aluno.id ? styles.selected : ''}`}
                    onClick={() => setAlunoSelecionado(aluno)}
                  >
                    <strong>{aluno.nome || 'Nome não disponível'}</strong>
                    <p>Matrícula: {aluno.matricula || 'N/A'} | Email: {aluno.email || 'N/A'}</p>
                  </li>
                ))
              ) : (
                <li className={styles.alunoListItem}>Nenhum aluno encontrado.</li>
              )}
            </ul>
          </div>
        )}

        {alunoSelecionado && (
          <div className={styles.alunoResult}>
            <h3>Aluno Selecionado:</h3>
            <p><strong>Nome:</strong> {alunoSelecionado.nome}</p>
            <button onClick={handleAssociate} disabled={associating} className={styles.confirmButton}>
              {associating ? 'Associando...' : `Confirmar Associação de ${alunoSelecionado.nome.split(' ')[0]}`}
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
