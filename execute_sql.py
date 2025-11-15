#!/usr/bin/env python3
import psycopg2
import os

# Leer el archivo SQL
sql_file = r"j:\UTP\9 CICLO\SOA\Documentos-SADDA\api\backend\src\main\resources\init.sql"

try:
    with open(sql_file, 'r', encoding='utf-8') as f:
        sql_content = f.read()
    
    # Conectar a PostgreSQL
    conn = psycopg2.connect(
        host="localhost",
        database="BDSADA",
        user="postgres",
        password="admin",
        port="5432"
    )
    
    cursor = conn.cursor()
    cursor.execute(sql_content)
    conn.commit()
    cursor.close()
    conn.close()
    
    print("✓ Script SQL ejecutado exitosamente en BDSADA")
    
except FileNotFoundError:
    print(f"✗ Archivo no encontrado: {sql_file}")
except psycopg2.Error as e:
    print(f"✗ Error de PostgreSQL: {e}")
except Exception as e:
    print(f"✗ Error: {e}")
