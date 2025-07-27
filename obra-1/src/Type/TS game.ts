export interface StockItem {
  name: string;
  quantity: number;
  icon: string;
}

export interface Stock {
  [key: string]: StockItem;
}

export interface GameStats {
  totalPedidosRecebidos: number;
  pedidosRejeitados: number;
  pedidosAtendidos: number;
  totalDeIngredientesComprados: number;
}

export interface Order {
  pizzaName: string;
  description: string;
  quantity: number;
}

export interface Delivery {
  id: number;
  name: string;
  quantity: number;
  totalTime: number;
  remainingTime: number;
  status: string;
}

export interface Purchase {
  id: number;
  key: string;
  name: string;
  icon: string;
  quantity: number;
  totalTime: number;
  remainingTime: number;
}

export interface UserCredentials {
  email: string;
  password: string;
  usuario: string;
}

export type GamePanel = 'home' | 'profile' | 'awards' | 'stock' | 'purchases' | 'delivery' | 'params'; 