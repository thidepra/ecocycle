package br.com.fiap.ecocycle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracaoSeguranca {

  @Bean
  public SecurityFilterChain filtroSeguranca(HttpSecurity http) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(configuracaoCors()))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/api/publica/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/pontos-coleta/**").hasAnyRole("BASICO", "GERENTE")
                    .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("BASICO", "GERENTE")
                    .requestMatchers("/api/**").hasRole("GERENTE")
                    .anyRequest().authenticated())
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .httpBasic(httpBasic -> httpBasic
                    .realmName("EcoCycle API"))
            .build();
  }

  @Bean
  public CorsConfigurationSource configuracaoCors() {
    CorsConfiguration configuracao = new CorsConfiguration();
    configuracao.setAllowedOrigins(Collections.singletonList("*"));
    configuracao.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuracao.setAllowedHeaders(Collections.singletonList("*"));
    configuracao.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource fonte = new UrlBasedCorsConfigurationSource();
    fonte.registerCorsConfiguration("/**", configuracao);
    return fonte;
  }

  @Bean
  public InMemoryUserDetailsManager gerenciadorUsuarios() {
    // Configurando com nomes e papéis completamente diferentes
    UserDetails usuarioConsulta = User.builder()
            .username("consulta")
            .password(codificadorSenha().encode("eco@Acesso2024"))
            .roles("BASICO")
            .build();

    UserDetails usuarioGestor = User.builder()
            .username("gestor")
            .password(codificadorSenha().encode("eco@Gestor2024!"))
            .roles("GERENTE")
            .build();

    return new InMemoryUserDetailsManager(usuarioConsulta, usuarioGestor);
  }

  @Bean
  public PasswordEncoder codificadorSenha() {
    // Configurando com força diferente do padrão
    return new BCryptPasswordEncoder(12);
  }
}