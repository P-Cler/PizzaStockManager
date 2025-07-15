import { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import { HomeProfessor } from './pages/homeProfessor/homeProfessor';
import { ModuloConfiguracaoDoJogo } from './components/compConfigJogo/compConfigJogo';
import TabelaCadastroAluno from './components/compCadastroAluno/compCadastroAluno';
import Login from './pages/login/login';
import { LoadingPage } from './pages/loadingPage/loadingPage';

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
        { id: 1, nome: "Pizza de Calabresa", custo: 15.50 },
        { id: 2, nome: "Bolo de Chocolate", custo: 25.00 },
        { id: 3, nome: "Sanduíche Natural", custo: 8.75 }
    ]);

    return (
        <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/loading" element={<LoadingPage />} />
            <Route path="/professor" element={<HomeProfessor />}>
                <Route
                    path="configuracao"
                    element={<ModuloConfiguracaoDoJogo receitas={receitas} setReceitas={setReceitas} />}
                />
                <Route path="alunos" element={<TabelaCadastroAluno />} />
                <Route path="ingredientes" element={<ModuloCadastroDeIngredientes />} />
                <Route
                    path="receita"
                    element={<ModuloReceitaDoProduto receitas={receitas} />}
                />
                <Route path="resultados" element={<ModuloResultados />} />
            </Route>
        </Routes>
    );
}

export default App;
