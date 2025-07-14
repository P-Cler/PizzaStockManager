import { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import { HomeProfessor } from './pages/homeProfessor/homeProfessor';
import { ModuloConfiguracaoDoJogo } from './components/moduloConfiguracaoDoJogo/moduloConfiguracaoDoJogo';
import CadastroAluno from './components/cadastroAluno/cadastroAluno';
import PedidosGamificados from './components/Pedidos/Pedidos';
import { PainelDoJogo } from './components/PainelDoJogo/PainelDoJogo';

const ModuloCadastroDeIngredientes = () => <h2>Página de Cadastro de Ingredientes</h2>;
const ModuloReceitaDoProduto = ({ receitas }) => (
    <div>
        <h2>Página de Receita do Produto</h2>
        <p>Receitas existentes: {receitas.length}</p>
    </div>
);
const ModuloResultados = () => <h2>Página de Resultados</h2>;

function App() {
    const [receitas, setReceitas] = useState([
        { id: 1, nome: "Pizza de Calabresa"},
        { id: 2, nome: "Pizza Três Porquinhos"},
    ]);

    return (
        <Routes>
            <Route path="/professor" element={<HomeProfessor />}>
                <Route
                    path="parametrizacao/:jogoId"
                    element={<ModuloConfiguracaoDoJogo receitas={receitas} setReceitas={setReceitas} />}
                />
                <Route path="alunos" element={<CadastroAluno />} />
                <Route path="ingredientes" element={<ModuloCadastroDeIngredientes />} />
                <Route
                    path="receita"
                    element={<ModuloReceitaDoProduto receitas={receitas} />}
                />
                <Route path="resultados" element={<ModuloResultados />} />
            </Route>
            <Route path="/jogo/:jogoId/painel" element={<PainelDoJogo />} />
        </Routes>
    );
}

export default App;