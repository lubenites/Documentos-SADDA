import { Usuario } from '../types';

const API_URL = 'http://localhost:8080/api';

export const servicioUsuarios = {
  obtenerUsuarios: async (): Promise<Usuario[]> => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${API_URL}/usuarios`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
      
      if (!response.ok) {
        console.error('Error obteniendo usuarios:', response.statusText);
        return [];
      }
      
      const usuarios = await response.json();
      return usuarios;
    } catch (error) {
      console.error('Error en servicioUsuarios.obtenerUsuarios:', error);
      return [];
    }
  },

  crearUsuario: async (datosUsuario: Omit<Usuario, 'id'>): Promise<Usuario> => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${API_URL}/usuarios`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosUsuario),
      });
      
      if (!response.ok) {
        throw new Error('Error creando usuario');
      }
      
      return await response.json();
    } catch (error) {
      console.error('Error en servicioUsuarios.crearUsuario:', error);
      throw error;
    }
  },

  actualizarUsuario: async (usuarioActualizado: Usuario): Promise<Usuario> => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${API_URL}/usuarios/${usuarioActualizado.id}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(usuarioActualizado),
      });
      
      if (!response.ok) {
        throw new Error('Error actualizando usuario');
      }
      
      return await response.json();
    } catch (error) {
      console.error('Error en servicioUsuarios.actualizarUsuario:', error);
      throw error;
    }
  },

  eliminarUsuario: async (usuarioId: number): Promise<boolean> => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${API_URL}/usuarios/${usuarioId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
      
      return response.ok;
    } catch (error) {
      console.error('Error en servicioUsuarios.eliminarUsuario:', error);
      return false;
    }
  },
};