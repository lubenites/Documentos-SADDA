-- ============================================================
-- A. Servicio de Usuarios (user_db)
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol_id INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- B. Servicio de Roles y Permisos (rbac_db)
-- ============================================================

-- 1. Tabla de roles
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT
);

-- 2. Tabla de permisos
CREATE TABLE IF NOT EXISTS permisos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT
);

-- 3. Tabla de relación: Asigna permisos a los roles
CREATE TABLE IF NOT EXISTS rol_permisos (
    rol_id INT NOT NULL,
    permiso_id INT NOT NULL,
    PRIMARY KEY (rol_id, permiso_id),
    CONSTRAINT fk_rp_rol FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_rp_permiso FOREIGN KEY (permiso_id) REFERENCES permisos(id) ON DELETE CASCADE
);

-- ============================================================
-- C. Servicio de Almacenamiento (storage_db)
-- ============================================================
CREATE TABLE IF NOT EXISTS archivos_metadata (
    id CHAR(36) PRIMARY KEY,
    path_almacenamiento VARCHAR(1024) UNIQUE NOT NULL,
    nombre_original VARCHAR(255) NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    tamano_bytes BIGINT NOT NULL,
    hash_contenido_sha256 CHAR(64) NOT NULL,
    subido_por_usuario_id BIGINT,
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_archivo_usuario FOREIGN KEY (subido_por_usuario_id) REFERENCES usuarios(id)
);

-- ============================================================
-- D. Servicio de Documentos (document_db)
-- ============================================================

-- 1. Tabla de documentos (OCs)
CREATE TABLE IF NOT EXISTS documentos (
    id BIGSERIAL PRIMARY KEY,
    oc VARCHAR(50) UNIQUE NOT NULL,
    creado_por_usuario_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50) NOT NULL DEFAULT 'Pendiente Compras',
    estado_entrega VARCHAR(50),
    CONSTRAINT fk_doc_usuario FOREIGN KEY (creado_por_usuario_id) REFERENCES usuarios(id)
);

-- 2. Tabla de anexos asociados al documento
CREATE TABLE IF NOT EXISTS archivos_asociados (
    id BIGSERIAL PRIMARY KEY,
    documento_id BIGINT NOT NULL,
    archivo_storage_id CHAR(36) NOT NULL,
    tipo_archivo VARCHAR(50) NOT NULL,
    area VARCHAR(50) NOT NULL,
    version INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_doc_arch FOREIGN KEY (documento_id) REFERENCES documentos(id) ON DELETE CASCADE,
    CONSTRAINT fk_archivo_metadata FOREIGN KEY (archivo_storage_id) REFERENCES archivos_metadata(id)
);

-- ============================================================
-- E. Servicio de Auditoría (audit_db)
-- ============================================================
CREATE TABLE IF NOT EXISTS historial_auditoria (
    id BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT NOT NULL,
    servicio_origen VARCHAR(100) NOT NULL,
    entidad_afectada VARCHAR(100) NOT NULL,
    entidad_id VARCHAR(100) NOT NULL,
    accion VARCHAR(255) NOT NULL,
    detalles JSONB,
    CONSTRAINT fk_audit_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- ============================================================
-- F. Servicio de Reportería (report_db)
-- ============================================================
CREATE TABLE IF NOT EXISTS resumen_documentos_kpis (
    fecha DATE PRIMARY KEY,
    total_creados INT,
    total_completados INT,
    tiempo_promedio_ciclo_minutos INT
);

-- ============================================================
-- Insertar roles por defecto
-- ============================================================
INSERT INTO roles (nombre, descripcion) VALUES
('ADMIN', 'Administrador del sistema'),
('GERENTE', 'Gerente de operaciones'),
('OPERARIO', 'Operario de almacén'),
('USUARIO', 'Usuario estándar')
ON CONFLICT (nombre) DO NOTHING;

-- ============================================================
-- Insertar permisos por defecto
-- ============================================================
INSERT INTO permisos (nombre, descripcion) VALUES
('CREATE_DOCUMENT', 'Crear documentos'),
('EDIT_DOCUMENT', 'Editar documentos'),
('DELETE_DOCUMENT', 'Eliminar documentos'),
('VIEW_DOCUMENT', 'Ver documentos'),
('MANAGE_USERS', 'Gestionar usuarios'),
('MANAGE_ROLES', 'Gestionar roles'),
('VIEW_AUDIT', 'Ver auditoría'),
('MANAGE_STORAGE', 'Gestionar almacenamiento')
ON CONFLICT (nombre) DO NOTHING;

-- ============================================================
-- Asignar permisos a roles (ADMIN tiene todos)
-- ============================================================
INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id FROM roles r, permisos p
WHERE r.nombre = 'ADMIN'
ON CONFLICT (rol_id, permiso_id) DO NOTHING;

-- ============================================================
-- Usuario por defecto (LIMPIAR PRIMERO)
-- ============================================================
DELETE FROM usuarios WHERE email = 'admin@sadda.com';

INSERT INTO usuarios (nombres, apellidos, email, password_hash, rol_id)
VALUES ('Admin', 'Sistema', 'admin@sadda.com', 'admin123', 1);
