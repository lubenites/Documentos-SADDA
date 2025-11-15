#!/usr/bin/env python3
import psycopg2

conn = psycopg2.connect(host='localhost', database='BDSADA', user='postgres', password='admin')
cursor = conn.cursor()

print('='*80)
print('ROLES')
print('='*80)
cursor.execute('SELECT id, nombre, descripcion FROM roles ORDER BY id;')
for row in cursor.fetchall():
    print(f'ID: {row[0]} | Nombre: {row[1]} | Descripción: {row[2]}')

print('\n' + '='*80)
print('PERMISOS')
print('='*80)
cursor.execute('SELECT id, nombre, descripcion FROM permisos ORDER BY id;')
for row in cursor.fetchall():
    print(f'ID: {row[0]} | Nombre: {row[1]} | Descripción: {row[2]}')

print('\n' + '='*80)
print('PERMISOS ASIGNADOS A ADMIN (rol_id = 1)')
print('='*80)
cursor.execute('''
  SELECT p.id, p.nombre, p.descripcion 
  FROM permisos p
  INNER JOIN rol_permisos rp ON p.id = rp.permiso_id
  WHERE rp.rol_id = 1
  ORDER BY p.id;
''')
permisos = cursor.fetchall()
if permisos:
    for row in permisos:
        print(f'ID: {row[0]} | Nombre: {row[1]} | Descripción: {row[2]}')
else:
    print('⚠️  ADMIN NO TIENE PERMISOS ASIGNADOS')

cursor.close()
conn.close()
