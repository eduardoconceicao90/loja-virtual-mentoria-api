package com.eduardo.lojavirtual.security;

import com.eduardo.lojavirtual.service.ImplUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

    @Autowired
    private ImplUserDetailsService implUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/index","/pagamento/**","/resources/**","/static/**","/templates/**","classpath:/static/**","classpath:/resources/**","classpath:/templates/**").permitAll()
                .antMatchers(HttpMethod.GET, "/requisicaojunoboleto/**","/requisicaoasaasboleto/**","/pagamento/**","/resources/**","/static/**","/templates/**","classpath:/static/**","classpath:/resources/**","classpath:/templates/**","/recuperarSenha").permitAll()
                .antMatchers(HttpMethod.POST, "/requisicaojunoboleto/**","/requisicaoasaasboleto/**","/pagamento/**","/resources/**","/static/**","/templates/**","classpath:/static/**","classpath:/resources/**","classpath:/templates/**","/recuperarSenha").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                /* Redireciona ou da um retorno para index quando desloga */
                .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

                /* Mapeia o logout do sistema */
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                /* Filtra as requisicoes para login de JWT */
                .and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /* Irá consultar o user no banco com Spring Security */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(implUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /* Ignora algumas URL livre de autenticação */
    @Override
    public void configure(WebSecurity web) throws Exception {
                  web.ignoring()
                          .antMatchers(HttpMethod.GET, "/requisicaojunoboleto/**","/requisicaoasaasboleto/**","/pagamento/**","/resources/**","/static/**","/templates/**","classpath:/static/**","classpath:/resources/**","classpath:/templates/**","/webjars/**","/WEB-INF/classes/static/**","/recuperarSenha")
                          .antMatchers(HttpMethod.POST, "/requisicaojunoboleto/**","/requisicaoasaasboleto/**","/pagamento/**","/resources/**","/static/**","/templates/**","classpath:/static/**","classpath:/resources/**","classpath:/templates/**","/webjars/**","/WEB-INF/classes/static/**","/recuperarSenha");
    }
}
