import React, { useState } from "react";
import styles from "./cadastroAluno.module.css";
import InputCampo from "../InputCampo/InputCampo";

export default function CadastroAluno({ isOpen, onClose }) {
  const [formData, setFormData] = useState({
    nome: "",
    matricula: "",
    email: "",
    turma: "",
  });
  const ButtonStyle = {
    backgroundColor: "#226034",
    color: "white",
    padding: "8px",
    border: "none",
    borderRadius: "8px",
    fontSize: "16px",
    marginTop: "15px",
    cursor: "pointer",
  };

  const [mensagem, setMensagem] = useState("");

  function handleChange(e) {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  }

  function handleSubmit(e) {
    e.preventDefault();
    setMensagem("Aluno cadastrado com sucesso!");
    setTimeout(() => {
      setMensagem("");
      onClose();
    }, 2000);
  }

  function handleClose() {
    const confirmar = window.confirm(
      "Tem certeza que deseja sair do cadastro?"
    );
    if (confirmar) {
      onClose();
      setMensagem("");
    }
  }

  if (!isOpen) return null;

  return (
    <div className={styles.overlay} onClick={handleClose}>
      <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
        <button className={styles.closeBtn} onClick={handleClose}>
          X
        </button>
        <h2>Cadastro de aluno</h2>
        <form onSubmit={handleSubmit}>
          <InputCampo
            label="Nome:"
            name="nome"
            placeholder="Digite seu nome"
            value={formData.nome}
            onChange={handleChange}
          />
          <InputCampo
            label="Código de Matrícula:"
            name="matricula"
            placeholder="Digite a matrícula"
            value={formData.matricula}
            onChange={handleChange}
          />
          <InputCampo
            label="E-mail:"
            name="email"
            placeholder="Digite seu e-mail"
            value={formData.email}
            onChange={handleChange}
          />
          <InputCampo
            label="Turma:"
            name="turma"
            placeholder="Digite sua turma"
            value={formData.turma}
            onChange={handleChange}
          />
          <button
            type="submit"
            classname={styles.button}
            style={ButtonStyle}
          >
            Salvar
          </button>
        </form>
        {mensagem && <p className={styles.sucesso}>{mensagem}</p>}
      </div>
    </div>
  );
}
