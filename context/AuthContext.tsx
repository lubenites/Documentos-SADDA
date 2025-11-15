
import React, { createContext, useState, useContext, ReactNode } from 'react';
import { Usuario, Permiso } from '../types';
import { servicioAuth } from '../api/authService';

interface AuthContextType {
  usuario: (Usuario & { permisos: Permiso[] }) | null;
  iniciarSesion: (email: string, password: string) => Promise<boolean>;
  cerrarSesion: () => void;
  cargando: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [usuario, setUsuario] = useState<(Usuario & { permisos: Permiso[] }) | null>(null);
  const [cargando, setCargando] = useState(false);

  const iniciarSesion = async (email: string, password: string): Promise<boolean> => {
    setCargando(true);
    try {
      console.log('🔐 AuthContext: Iniciando sesión para', email);
      const usuarioLogueado = await servicioAuth.iniciarSesion(email, password);
      console.log('✅ AuthContext: Usuario obtenido:', usuarioLogueado);
      if (usuarioLogueado) {
        console.log('✅ AuthContext: Guardando usuario en estado');
        setUsuario(usuarioLogueado);
        return true;
      }
      console.error('❌ AuthContext: No se obtuvo usuario');
      return false;
    } catch (error) {
      console.error('❌ AuthContext: Error en autenticación:', error);
      return false;
    } finally {
      setCargando(false);
    }
  };

  const cerrarSesion = () => {
    setUsuario(null);
  };

  return (
    <AuthContext.Provider value={{ usuario, iniciarSesion, cerrarSesion, cargando }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth debe ser utilizado dentro de un AuthProvider');
  }
  return context;
};
