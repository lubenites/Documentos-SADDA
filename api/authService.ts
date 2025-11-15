
import { Usuario, Permiso } from '../types';

const API_URL = 'http://localhost:8080/api';

export const servicioAuth = {
  iniciarSesion: async (email: string, password: string): Promise<(Usuario & { permisos: Permiso[] }) | null> => {
    try {
      console.log('🔐 Intentando conectar a:', `${API_URL}/auth/login`);
      console.log('📧 Email:', email);
      
      const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      console.log('📡 Respuesta del servidor:', response.status, response.statusText);

      if (!response.ok) {
        const errorData = await response.text();
        console.error('❌ Error en autenticación:', response.status, errorData);
        return null;
      }

      const data = await response.json();
      console.log('✅ Token recibido:', data.token ? 'Sí' : 'No');
      console.log('👤 Datos del usuario:', data);
      console.log('🔑 Permisos:', data.permisos);
      
      // Guardar el token
      localStorage.setItem('token', data.token);

      // Convertir permisos del backend al formato del frontend
      const permisos: Permiso[] = (data.permisos || []).map((p: any) => ({
        id: p.id,
        nombre: p.nombre,
        descripcion: p.descripcion,
      }));

      // Retornar usuario con datos del backend
      return {
        id: data.userId || 1,
        nombres: data.nombres || 'Usuario',
        apellidos: data.apellidos || 'Autenticado',
        email: data.email || email,
        rolId: data.rolId || 1,
        permisos: permisos,
      } as (Usuario & { permisos: Permiso[] });
    } catch (error) {
      console.error('❌ Error conectando al backend:', error);
      return null;
    }
  },
};
