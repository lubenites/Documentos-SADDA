package com.gestordocs.authservice.service;

import com.gestordocs.authservice.model.LoginRequest;
import com.gestordocs.authservice.model.LoginResponse;
import com.gestordocs.authservice.model.Permiso;
import com.gestordocs.authservice.model.PermisoDTO;
import com.gestordocs.authservice.model.Usuario;
import com.gestordocs.authservice.repository.PermisoRepository;
import com.gestordocs.authservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private ServicioToken servicioToken;

    public LoginResponse autenticar(LoginRequest request) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(request.getEmail());

        if (usuario.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }

        Usuario u = usuario.get();
        
        // Comparar contraseña (en producción usar BCrypt)
        if (!u.getPassword_hash().equals(request.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        // Obtener permisos del rol
        List<Permiso> permisos = permisoRepository.findByRolId(u.getRolId());
        List<PermisoDTO> permisosDTO = permisos.stream()
                .map(p -> new PermisoDTO(p.getId(), p.getNombre(), p.getDescripcion()))
                .collect(Collectors.toList());

        // Generar token
        String token = servicioToken.generarToken(u.getId(), u.getRolId(), u.getEmail());
        return new LoginResponse(token, "Autenticación exitosa", u.getId(), u.getEmail(), u.getNombres(), u.getApellidos(), u.getRolId(), permisosDTO);
    }

    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

}

