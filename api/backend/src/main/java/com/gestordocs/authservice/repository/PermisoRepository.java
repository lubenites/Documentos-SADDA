package com.gestordocs.authservice.repository;

import com.gestordocs.authservice.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    @Query(value = "SELECT p.* FROM permisos p " +
                   "INNER JOIN rol_permisos rp ON p.id = rp.permiso_id " +
                   "WHERE rp.rol_id = :rolId", nativeQuery = true)
    List<Permiso> findByRolId(@Param("rolId") Integer rolId);
}
