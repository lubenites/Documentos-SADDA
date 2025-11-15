# SADDA Backend - Instrucciones de Configuración

## 📋 Configuración de la Base de Datos

### 1. Crear la base de datos en PostgreSQL

```bash
psql -U postgres -c "CREATE DATABASE sadda_db;"
```

### 2. Ejecutar el script SQL

```bash
psql -U postgres -d sadda_db -f api/backend/src/main/resources/init.sql
```

O desde pgAdmin:
1. Conéctate a PostgreSQL
2. Crea una nueva base de datos llamada `sadda_db`
3. Abre Query Tool
4. Copia y pega el contenido del archivo `init.sql`
5. Ejecuta

### 3. Verificar que las tablas se crearon

```bash
psql -U postgres -d sadda_db -c "\dt"
```

## 🚀 Ejecutar el Backend

```bash
cd api/backend
mvn clean install -DskipTests
mvn spring-boot:run
```

El servidor estará disponible en: **http://localhost:8080**

## 📝 Credenciales por defecto

- **Email**: `admin@sadda.com`
- **Password**: `admin123`

## 🔌 Endpoints disponibles

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `GET /api/auth/validate` - Validar token

### Documentos
- `GET /api/documentos` - Listar todos
- `POST /api/documentos` - Crear documento
- `GET /api/documentos/{id}` - Obtener por ID
- `PUT /api/documentos/{id}` - Actualizar
- `DELETE /api/documentos/{id}` - Eliminar

## 📊 Estructura de tablas

- **usuarios** - Datos de usuarios
- **roles** - Roles del sistema
- **permisos** - Permisos disponibles
- **rol_permisos** - Relación roles-permisos
- **documentos** - Documentos (OCs)
- **archivos_asociados** - Archivos vinculados a documentos
- **archivos_metadata** - Información de archivos
- **historial_auditoria** - Registro de auditoría
- **resumen_documentos_kpis** - Reportes y KPIs

## ⚠️ Notas importantes

1. **Seguridad**: En producción, cambiar las contraseñas por defecto
2. **Hash de contraseñas**: Actualmente usa texto plano. Usar BCrypt en producción
3. **JWT**: El token expira en 24 horas (configurable en `application.properties`)
