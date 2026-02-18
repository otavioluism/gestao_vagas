package com.olmcompany.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    @Value("${security.token.secret.company}")
    private String secretKey;

    public DecodedJWT validatedToken(String header) {
        String token = header.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token);

        } catch (JWTVerificationException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
