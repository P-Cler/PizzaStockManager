import { Routes, Route } from "react-router-dom";

export function AppRoutes() {
  return (
    <Routes>
      <Route />
       <Route path="/" element={<Login />} />
        <Route path="/recuperar-senha" element={<RecuperarSenha />} />
    </Routes>
  );
}
