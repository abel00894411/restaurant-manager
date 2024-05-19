import { createContext } from 'react';
import Menu from '../models/Menu';

const MenuContext = createContext<Menu | undefined>(undefined);

export default MenuContext;