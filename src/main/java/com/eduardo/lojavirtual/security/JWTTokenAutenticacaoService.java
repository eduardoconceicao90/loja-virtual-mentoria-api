package com.eduardo.lojavirtual.security;

import com.eduardo.lojavirtual.config.ApplicationContextLoad;
import com.eduardo.lojavirtual.model.Usuario;
import com.eduardo.lojavirtual.repository.UsuarioRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/* Criar a autenticação e retornar também a autenticação JWT */
@Service
public class JWTTokenAutenticacaoService {

    /* Token tem validade de 10 dias */
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

        liberacaoCors(response);

        Usuario usuario = ApplicationContextLoad
                .getApplicationContext()
                .getBean(UsuarioRepository.class).findUserByLogin(username);

        /* Usado para ver no Postman para teste */
        response.getWriter().write("{\"Authorization\": \"" + token
                                        + "\", \"username\": \"" + username
                                        + "\", \"empresa\": \"" + usuario.getEmpresa().getId()
                                         + "\", \"idUser\": \"" + usuario.getId()
                                        + "\"}");
    }

    /* Retorna o usuário validado com token ou caso nao seja valido retona null */
    public Authentication getAuthetication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String token = request.getHeader(HEADER_STRING);

        try {
            if (token != null) {
                String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

                /* Faz a validacao do token do usuário na requisicao e obtem o USER */
                String user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(tokenLimpo)
                        .getBody().getSubject(); /* ADMIN ou Eduardo */

                if (user != null) {
                    Usuario usuario = ApplicationContextLoad
                            .getApplicationContext()
                            .getBean(UsuarioRepository.class).findUserByLogin(user);
                    if (usuario != null) {
                        return new UsernamePasswordAuthenticationToken(
                                usuario.getLogin(),
                                usuario.getSenha(),
                                usuario.getAuthorities());
                    }
                }
            }

        } catch (SignatureException e){
            response.getWriter().write("Token está inválido");
        } catch (ExpiredJwtException e){
            response.getWriter().write("Token está expirado, efetue o login novamente");
        } finally {
            liberacaoCors(response);
        }

        liberacaoCors(response);
        return null;
    }

    /* Fazendo liberação contra erro de Cors no navegador */
    private void liberacaoCors(HttpServletResponse response){

        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }

        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }
}
