import React, {createContext, useMemo, useState, useContext} from 'react'
import { ThemeProvider as MuiThemeProvider, createTheme } from '@mui/material/styles'

const ThemeContext = createContext();

export function useCustomTheme(){
    return useContext(ThemeContext);
}

export function ThemeProvider({children}) {
    const [mode, setMode] = useState('light');

const toggleTheme = () =>{
    setMode((prev) => (prev === 'light' ? 'dark': 'light'));
}

const theme = useMemo(
    () => 
        createTheme({
            palette: {
                mode, 
                primary: {
                    main: '#1976d2',
                },
                secondary: {
                    main: '#dc004e'
                },
            },
        }),
        [mode]
    );

    return (
        <ThemeContext.Provider value={{mode, toggleTheme }}>
            <MuiThemeProvider theme={theme}>{children}</MuiThemeProvider>
        </ThemeContext.Provider>
    )

}