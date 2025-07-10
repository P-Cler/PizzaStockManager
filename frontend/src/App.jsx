import { Routes, Route } from 'react-router-dom';
import HomeProfessor from './pages/homeProfessor/homeProfessor';
// import Login from './Sobre';

function App() {
  return (
    <Routes>
      <Route path="/home" element={<HomeProfessor />} />
      {/* <Route path="/login" element={<Login />} /> */}
    </Routes>
  );
}

export default App;
