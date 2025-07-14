// ModuloCadastroAlunos.jsx
import React, { useEffect, useState } from "react";
import styles from "@/components/moduloCadastroAluno/moduloCadastroDoAluno.module.css"

export function ModuloCadastroAlunos() {
  const [alunos, setAlunos] = useState([]);
  const [paginaAtual, setPaginaAtual] = useState(1);
  const alunosPorPagina = 20;


  useEffect(() => {
    fetch("/api/alunos")
      .then((res) => res.json())
      .then((data) => setAlunos(data));
  }, []);

  const indiceInicial = (paginaAtual - 1) * alunosPorPagina;
  const indiceFinal = indiceInicial + alunosPorPagina;
  const alunosPagina = alunos.slice(indiceInicial, indiceFinal);
  const totalPaginas = Math.ceil(alunos.length / alunosPorPagina);

  function mudarPagina(novaPagina) {
    setPaginaAtual(novaPagina);
  }

  return (
    <section aria-labelledby="titulo-alunos" className={styles["alunos-section"]}>
      <h2 id="titulo-alunos">Cadastro de Alunos</h2>
      <div className={styles["tabela-container"]} tabIndex="0" aria-label="Lista de alunos">
        <table className={styles["tabela-container"]}>
          <thead>
            <tr>
              <th scope="col">Nome do Aluno</th>
              <th scope="col">Cód. Matrícula</th>
              <th scope="col">E-mail</th>
              <th scope="col">Turma</th>
              <th scope="col">Ações</th>
            </tr>
          </thead>
          <tbody>
            {alunosPagina.map((aluno) => (
              <tr key={aluno.id}>
                <td>{aluno.nome}</td>
                <td>{aluno.matricula}</td>
                <td>{aluno.email}</td>
                <td>{aluno.turma}</td>
                <td>
                  <button
                    type="button"
                    aria-label={`Remover ${aluno.nome}`}
                    className={styles["btn-remover"]}
                  >
                    🗑️
                  </button>
                </td>
              </tr>
            ))}
            {alunosPagina.length === 0 && (
              <tr>
                <td colSpan="5">Nenhum aluno encontrado.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
      <nav
        className={styles.paginacao}
        aria-label="Paginação de alunos"
      >
        <button
          type="button"
          onClick={() => mudarPagina(paginaAtual - 1)}
          disabled={paginaAtual === 1}
          aria-label="Página anterior"
        >
          &lt;
        </button>
        <span>
          Página {paginaAtual} de {totalPaginas}
        </span>
        <button
          type="button"
          onClick={() => mudarPagina(paginaAtual + 1)}
          disabled={paginaAtual === totalPaginas}
          aria-label="Próxima página"
        >
          &gt;
        </button>
      </nav>
    </section>
  );
}
