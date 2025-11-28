import { Usuario, Permiso } from '../types';

const API_URL = 'http://localhost:3002/api/auth';

export const servicioAuth = {
  iniciarSesion: async (email: string, password: string): Promise<Usuario | null> => {
    try {
      console.log('Intentando iniciar sesión en:', `${API_URL}/login`);

      const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        console.error('Error en autenticación:', response.status);
        return null;
      }

      const data = await response.json();
      console.log("TOKEN recibido:", data.token);

      // Guardar token
      localStorage.setItem("token", data.token);

      return {
        id: data.userId,
        nombres: data.nombres,
        apellidos: data.apellidos,
        email: email,
        rolId: data.rolId,
      };

    } catch (error) {
      console.error("Error conectando al backend:", error);
      return null;
    }
  }
};
