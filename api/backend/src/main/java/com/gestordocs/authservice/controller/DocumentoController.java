package com.gestordocs.authservice.controller;

import com.gestordocs.authservice.model.Documento;
import com.gestordocs.authservice.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping
    public ResponseEntity<?> crearDocumento(@RequestBody Documento documento) {
        try {
            Documento nuevoDocumento = documentoService.crearDocumento(documento);
            return ResponseEntity.ok(nuevoDocumento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDocumento(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(documentoService.obtenerDocumentoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerDocumentosPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Documento> documentos = documentoService.obtenerDocumentosPorUsuario(usuarioId);
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<?> obtenerDocumentosPorArea(@PathVariable Integer areaId) {
        try {
            List<Documento> documentos = documentoService.obtenerDocumentosPorArea(areaId);
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosDocumentos() {
        try {
            List<Documento> documentos = documentoService.obtenerTodosLosDocumentos();
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDocumento(@PathVariable Long id, @RequestBody Documento documento) {
        try {
            Documento documentoActualizado = documentoService.actualizarDocumento(id, documento);
            return ResponseEntity.ok(documentoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDocumento(@PathVariable Long id) {
        try {
            documentoService.eliminarDocumento(id);
            return ResponseEntity.ok("Documento eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
