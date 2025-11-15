#!/usr/bin/env python3
import psycopg2

try:
    conn = psycopg2.connect(
        host="localhost",
        database="BDSADA",
        user="postgres",
        password="admin"
    )
    cursor = conn.cursor()
    
    # Verificar qué hay en usuarios
    cursor.execute("SELECT id, email, password_hash, rol_id FROM usuarios;")
    rows = cursor.fetchall()
    
    print("📊 Usuarios en la BD:")
    print("=" * 80)
    for row in rows:
        print(f"ID: {row[0]} | Email: {row[1]} | Password: {row[2]} | Rol: {row[3]}")
    
    cursor.close()
    conn.close()
    
except Exception as e:
    print(f"❌ Error: {e}")
