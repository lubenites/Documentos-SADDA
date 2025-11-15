package com.gestordocs.authservice.repository;

import com.gestordocs.authservice.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByUsuarioIdAndActivo(Long usuarioId, Boolean activo);
    List<Documento> findByAreaIdAndActivo(Integer areaId, Boolean activo);
    List<Documento> findByActivo(Boolean activo);
}
