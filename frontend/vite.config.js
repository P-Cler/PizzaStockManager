import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
<<<<<<< HEAD
=======
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
>>>>>>> 8ff2a64c3a506bc8c193bfd8a304c51c366171b9

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
<<<<<<< HEAD
=======
  resolve:{
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
>>>>>>> 8ff2a64c3a506bc8c193bfd8a304c51c366171b9
})
