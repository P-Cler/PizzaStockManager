import { useState } from 'react';
import { LoginScreen } from './components/LoginScreen';
import { LoadingScreen } from './components/LoadingScreen';
import { GameScreen } from './components/GameScreen';
import { UserCredentials } from './types/game';

type AppState = 'login' | 'loading' | 'game';

function App() {
  const [appState, setAppState] = useState<AppState>('login');
  const [playerName, setPlayerName] = useState('');

  const handleLogin = (credentials: UserCredentials) => {
    setPlayerName(credentials.usuario);
    setAppState('loading');
  };

  const handleLoadingComplete = () => {
    setAppState('game');
  };

  const renderCurrentScreen = () => {
    switch (appState) {
      case 'login':
        return <LoginScreen onLogin={handleLogin} />;
      case 'loading':
        return <LoadingScreen onComplete={handleLoadingComplete} />;
      case 'game':
        return <GameScreen playerName={playerName} />;
      default:
        return <LoginScreen onLogin={handleLogin} />;
    }
  };

  return (
    <div className="min-h-screen">
      {renderCurrentScreen()}
    </div>
  );
}

export default App; 