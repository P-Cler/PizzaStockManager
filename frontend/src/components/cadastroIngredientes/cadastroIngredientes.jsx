import React, { useState } from 'react';
import styles from './cadastroIngredientes.module.css';
import InputCampo from '../InputCampo/InputCampo';

export default function CadastroIngrediente({ isOpen, onClose }) {
  const [formData, setFormData] = useState({
    ingrediente: '',
    unidade: '',
    estoqueAtual: '',
    estoqueMinimo: 10,
    estoqueMaximo: 180,
    leadTime: '',
    pontoDeSeguranca: ''
  });

  const [mensagem, setMensagem] = useState('');

  function handleChange(e) {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  }

  function handleSubmit(e) {
    e.preventDefault();
    //atenção aqui,ver com galera do back 
    setMensagem('Ingrediente cadastrado com sucesso!');
    setTimeout(() => {
      setMensagem('');
      onClose();
    }, 2000);
  }

  function handleClose() {
    const confirmar = window.confirm("Deseja realmente fechar? Os dados serão perdidos.");
    if (confirmar) {
      onClose();
      setMensagem('');
    }
  }

  if (!isOpen) return null;

  return (
    <div className={styles.overlay} onClick={handleClose}>
      <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
        <button className={styles.closeBtn} onClick={handleClose}>X</button>
        <h2>Cadastro de Ingrediente</h2>
        <form onSubmit={handleSubmit}>
          <InputCampo
            label="Ingrediente:"
            name="ingrediente"
            placeholder="Digite o nome do ingrediente"
            value={formData.ingrediente}
            onChange={handleChange}
          />
          <InputCampo
            label="Unidade:"
            name="unidade"
            placeholder="Ex: kg, L, unidade"
            value={formData.unidade}
            onChange={handleChange}
          />
          <InputCampo
            label="Estoque Atual:"
            name="estoqueAtual"
            placeholder="Quantidade atual"
            value={formData.estoqueAtual}
            onChange={handleChange}
          />
          <InputCampo
            label="Estoque Mínimo:"
            name="estoqueMinimo"
            placeholder="Mínimo permitido"
            value={formData.estoqueMinimo}
            onChange={handleChange}
          />
          <InputCampo
            label="Estoque Máximo:"
            name="estoqueMaximo"
            placeholder="Máximo permitido"
            value={formData.estoqueMaximo}
            onChange={handleChange}
          />
          <InputCampo
            label="Lead Time (dias):"
            name="leadTime"
            placeholder="Tempo de reposição"
            value={formData.leadTime}
            onChange={handleChange}
          />
          <InputCampo
            label="Ponto de Segurança:"
            name="pontoDeSeguranca"
            placeholder="Nível de segurança"
            value={formData.pontoDeSeguranca}
            onChange={handleChange}
          />
          <button
            type="submit"
            className={styles.botaoSalvar}
            style={{ backgroundColor: "#226034" }}
          >
            Salvar
          </button>
        </form>
        {mensagem && <p className={styles.sucesso}>{mensagem}</p>}
      </div>
    </div>
  );
}
