package com.eduardo.lojavirtual.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/* Criar a autenticação e retornar também a autenticação JWT */
@Service
public class JWTTokenAutenticacaoService {

    /* Token de validade de 10 dias */
    private static final long EXPIRATION_TIME = 864000000;

    /* Chave de senha para juntar com o JWT */
    private static final String SECRET = "teste_123";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    /* Gera o token e da a resposta para o cliente com JWT */
    public void addAuthentication(HttpServletResponse response, String username) throws Exception {

        /* Montagem do Token */
        String JWT = Jwts.builder() /* Chama o gerador de token */
                .setSubject(username) /* Adiciona o user */
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* Tempo de expiração */
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();

        String token = TOKEN_PREFIX + " " + JWT;

        /* Dá resposta para tela e para o cliente (outra API, navegar, aplicativo, etc.) */
        response.addHeader(HEADER_STRING, token);

        /* Usado para ver no Postman para teste */
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }
}
