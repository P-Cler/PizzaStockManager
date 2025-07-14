import React, { useEffect, useState, useCallback } from "react";
import { useParams } from 'react-router-dom';
import styles from "./moduloCadastroDoAluno.module.css"; 
import { ModalBuscaAluno } from "../modalBuscaAluno/ModalBuscarAluno";
import { Trash, PlusCircleIcon } from 'lucide-react'; 

const API_URL = import.meta.env.VITE_API_URL;

export function ModuloAlunosAssociados() {
  const { jogoId } = useParams();
  const [alunosAssociados, setAlunosAssociados] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  
  const fetchAlunosAssociados = useCallback(async () => {
    if (!jogoId) return;
    setLoading(true);
    setError(null); 
    try {
      const response = await fetch(`${API_URL}/jogos-alunos/jogo/${jogoId}`);
      if (!response.ok) {
        if (response.status === 404) {
          setAlunosAssociados([]);
          return;
        }
        throw new Error("Não foi possível carregar os alunos do jogo.");
      }

      const data = await response.json();

      const normalized = data.map(aluno => ({
        ...aluno,
        nome: aluno.nome?.trim() || 'Nome não informado',
        email: aluno.email?.trim() || 'E-mail não informado',
        matricula: aluno.matricula || 'N/A',
      }));

      setAlunosAssociados(normalized);
    } catch (err) {
      setError(err.message);
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, [jogoId]);

  useEffect(() => {
    fetchAlunosAssociados();
  }, [fetchAlunosAssociados]);

  const handleDesassociar = async (alunoId) => {
    if (!window.confirm("Tem certeza que deseja remover este aluno do jogo?")) {
      return;
    }
    
    try {
      const response = await fetch(`${API_URL}/jogos-alunos/jogo/${jogoId}/aluno/${alunoId}`, {
        method: 'DELETE',
      });
      if (!response.ok) {
        throw new Error("Falha ao remover o aluno do jogo.");
      }
      alert("Aluno removido do jogo com sucesso!");
      fetchAlunosAssociados();
    } catch (err) {
      setError(err.message); 
      alert(err.message);
    }
  };

  const handleAssociationSuccess = () => {
    setIsModalOpen(false);
    fetchAlunosAssociados();
  };
  
  return (
    <>
      <div className={styles.container}>
        <h2 className={styles.title}>Alunos Associados ao Jogo</h2>

        {loading && <p className={styles.centered}>Carregando...</p>}
        
        {error && <div className={`${styles.centered} ${styles.error}`}>Erro: {error}</div>}

        {!loading && !error && (
          <div className={styles.tableWrapper}>
            <table className={styles.table}>
              <thead className={styles.thead}>
                <tr>
                  <th>Nome do Aluno</th>
                  <th>E-mail</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {alunosAssociados.length > 0 ? (
                  alunosAssociados.map((aluno) => (
                    <tr key={aluno.id}>
                      <td>{aluno.nome}</td>
                      <td>{aluno.email}</td>
                      <td className={styles.actions}>
                        <button
                          onClick={() => handleDesassociar(aluno.id)}
                          className={styles.deleteButton}
                          aria-label={`Desassociar ${aluno.nome}`}
                        >
                          <Trash />
                        </button>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="3" className={styles.noData}>
                      Nenhum aluno associado a este jogo.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        )}

        <div className={styles.addButtonContainer}>
            <button onClick={() => setIsModalOpen(true)} className={styles.addButton}>
                <PlusCircleIcon /> Associar Aluno
            </button>
        </div>
      </div>

      {isModalOpen && (
        <ModalBuscaAluno
          jogoId={jogoId}
          onClose={() => setIsModalOpen(false)}
          onAssociateSuccess={handleAssociationSuccess}
        />
      )}
    </>
  );
}