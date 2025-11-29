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
        const errorBody = await response.json().catch(() => null);
        console.error('❌ Error en login:', response.status, errorBody);
        return null;
      }

      const data = await response.json();
      console.log('✅ Respuesta de login:', data);

      // ¡OJO! aquí usamos userID, no userId
      return {
        id: data.userID,
        nombres: data.nombres,
        apellidos: data.apellidos,
        email: data.email ?? email,
        rolId: Number(data.rolId),
        permisos: data.permisos ?? [],
      };

    } catch (error) {
      console.error('Error conectando al backend:', error);
      return null;
    }
  }
};
