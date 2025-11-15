package com.gestordocs.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServicioToken {

    @Value("${jwt.secret.key}")
    private String claveSecreta;

    @Value("${jwt.expiration.ms}")
    private long tiempoExpiracionMs;

    private Key claveFirma;

    private Key getClaveFirma() {
        if (this.claveFirma == null) {
            this.claveFirma = Keys.hmacShaKeyFor(claveSecreta.getBytes());
        }
        return this.claveFirma;
    }

    public String generarToken(Long usuarioId, Integer rolId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id_usuario", usuarioId);
        claims.put("rol", rolId);
        claims.put("email", email);

        Date ahora = new Date();
        Date expira = new Date(ahora.getTime() + tiempoExpiracionMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuarioId.toString())
                .setIssuedAt(ahora)
                .setExpiration(expira)
                .signWith(getClaveFirma(), SignatureAlgorithm.HS256)
                .compact();
    }
}
