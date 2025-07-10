import { BrowserRouter, Routes, Route } from 'react-router-dom';
import CadastroAluno from './components/cadastroAluno/cadastroAluno';
import { HomeProfessor } from './pages/homeProfessor/homeProfessor';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/professor" element={<HomeProfessor />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;