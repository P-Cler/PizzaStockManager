// ModuloConfiguracaoJogo.jsx
import React, { useState } from "react";
import styles from "@/components/compConfigJogo/compConfigJogo.module.css";

export function ModuloConfiguracaoDoJogo({ receitas }) {
  const [form, setForm] = useState({
    tempoTotal: "",
    cicloPedido: "",
    numCiclos: "",
    maxProdutos: "",
    receita: "",
    minProdutos: "",
  });

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  return (
    <form
      className={styles["config-jogo-form"]}
      aria-labelledby="config-jogo-titulo"
    >
      <fieldset>
        <legend id="config-jogo-titulo">Configuração do Jogo</legend>
        <div className={styles["form-row"]}>
          <label htmlFor="tempoTotal">Tempo total do jogo:</label>
          <input
            id="tempoTotal"
            name="tempoTotal"
            type="text"
            value={form.tempoTotal}
            onChange={handleChange}
            placeholder="45 min"
            required
          />
        </div>
        <div className={styles["form-row"]}>
          <label htmlFor="cicloPedido">Tempo do ciclo do pedido:</label>
          <input
            id="cicloPedido"
            name="cicloPedido"
            type="text"
            value={form.cicloPedido}
            onChange={handleChange}
            placeholder="Automático"
            required
          />
        </div>
        <div className={styles["form-row"]}>
          <label htmlFor="numCiclos">Número de ciclos (pedido):</label>
          <input
            id="numCiclos"
            name="numCiclos"
            type="number"
            value={form.numCiclos}
            onChange={handleChange}
            min="1"
            required
          />
        </div>
        <div className={styles["form-row"]}>
          <label htmlFor="minProdutos">Mínimo de produtos por pedido:</label>
          <input
            id="minProdutos"
            name="minProdutos"
            type="number"
            value={form.minProdutos}
            onChange={handleChange}
            min="1"
            required
          />
        </div>
        <div className={styles["form-row"]}>
          <label htmlFor="maxProdutos">Máximo de produtos por pedido:</label>
          <input
            id="maxProdutos"
            name="maxProdutos"
            type="number"
            value={form.maxProdutos}
            onChange={handleChange}
            min="1"
            required
          />
        </div>
        <div className={styles["form-row"]}>
          <label htmlFor="receita">Sabor da Pizza:</label>
          <select
            id="receita"
            name="receita"
            value={form.receita}
            onChange={handleChange}
            required
          >
            <option value="">Selecione...</option>
            {Array.isArray(receitas) &&
              receitas.map((r, index) => (
                <option key={r.id ?? index} value={r.id ?? r}>
                  {r.nome ?? r}
                </option>
              ))}
            <option value="novo">Criar novo sabor</option>
          </select>
        </div>
      </fieldset>
    </form>
  );
}
