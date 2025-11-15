package com.gestordocs.authservice.model;

import java.util.List;

public class LoginResponse {
    private String token;
    private String message;
    private Long userId;
    private String email;
    private String nombres;
    private String apellidos;
    private Integer rolId;
    private List<PermisoDTO> permisos;

    public LoginResponse() {}

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public LoginResponse(String token, String message, Long userId, String email, String nombres, String apellidos, Integer rolId) {
        this.token = token;
        this.message = message;
        this.userId = userId;
        this.email = email;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.rolId = rolId;
    }

    public LoginResponse(String token, String message, Long userId, String email, String nombres, String apellidos, Integer rolId, List<PermisoDTO> permisos) {
        this.token = token;
        this.message = message;
        this.userId = userId;
        this.email = email;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.rolId = rolId;
        this.permisos = permisos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public List<PermisoDTO> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisoDTO> permisos) {
        this.permisos = permisos;
    }
}
