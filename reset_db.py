#!/usr/bin/env python3
import psycopg2
from psycopg2 import sql

# Conexión a PostgreSQL
try:
    conn = psycopg2.connect(
        host="localhost",
        database="BDSADA",
        user="postgres",
        password="admin"
    )
    cursor = conn.cursor()
    
    # Limpiar usuario anterior
    print("🧹 Limpiando usuario anterior...")
    cursor.execute("DELETE FROM usuarios WHERE email = 'admin@sadda.com';")
    
    # Insertar nuevo usuario con credenciales correctas
    print("✏️ Insertando nuevo usuario admin...")
    cursor.execute("""
        INSERT INTO usuarios (nombres, apellidos, email, password_hash, rol_id)
        VALUES ('Admin', 'Sistema', 'admin@sadda.com', 'admin123', 1)
    """)
    
    conn.commit()
    print("✅ Usuario actualizado correctamente!")
    print("📧 Email: admin@sadda.com")
    print("🔑 Password: admin123")
    
    cursor.close()
    conn.close()
    
except Exception as e:
    print(f"❌ Error: {e}")
