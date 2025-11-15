package com.gestordocs.authservice.service;

import com.gestordocs.authservice.model.Documento;
import com.gestordocs.authservice.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    public Documento crearDocumento(Documento documento) {
        return documentoRepository.save(documento);
    }

    public Optional<Documento> obtenerDocumentoPorId(Long id) {
        return documentoRepository.findById(id);
    }

    public List<Documento> obtenerDocumentosPorUsuario(Long usuarioId) {
        return documentoRepository.findByUsuarioIdAndActivo(usuarioId, true);
    }

    public List<Documento> obtenerDocumentosPorArea(Integer areaId) {
        return documentoRepository.findByAreaIdAndActivo(areaId, true);
    }

    public List<Documento> obtenerTodosLosDocumentos() {
        return documentoRepository.findByActivo(true);
    }

    public Documento actualizarDocumento(Long id, Documento documento) throws Exception {
        Optional<Documento> existente = documentoRepository.findById(id);
        if (existente.isEmpty()) {
            throw new Exception("Documento no encontrado");
        }

        Documento doc = existente.get();
        doc.setTitulo(documento.getTitulo());
        doc.setDescripcion(documento.getDescripcion());
        doc.setRuta_archivo(documento.getRuta_archivo());
        doc.setAreaId(documento.getAreaId());

        return documentoRepository.save(doc);
    }

    public void eliminarDocumento(Long id) throws Exception {
        Optional<Documento> documento = documentoRepository.findById(id);
        if (documento.isEmpty()) {
            throw new Exception("Documento no encontrado");
        }

        Documento doc = documento.get();
        doc.setActivo(false);
        documentoRepository.save(doc);
    }

}
