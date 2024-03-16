package com.ventasbackend.service.serviceimpl;

import com.ventasbackend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    public String generateToken(User user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

        return Jwts.builder()
                .claims(extraClaims) //deben ser los primeros en consruirse
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    //Generar clave secreta que solo el servidor conoce para firmar el token.
    private SecretKey generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public Claims extractAllClaims(String jwt) {

       return Jwts.parser()
               .verifyWith(generateKey()) //Configura el verificador para que utilice la clave generada
               .build()
               .parseSignedClaims(jwt)// Analiza el JWT y verifica su firma utilizando el verificador configurado en el paso anterior.
               .getPayload();
    }
}
