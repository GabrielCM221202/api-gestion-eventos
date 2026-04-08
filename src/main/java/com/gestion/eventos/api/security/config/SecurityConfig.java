package com.gestion.eventos.api.security.config;

import com.gestion.eventos.api.security.jwt.JwtAuthenticationFilter;
import com.gestion.eventos.api.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPoint jwtAuthEntryPoint;
    private final JwtAuthenticationFilter  jwtAuthenticationFilter;
    private final Environment environment;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors(
                        cors ->
                                cors.configurationSource(this.corsConfigurationSource())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception ->
                    exception.authenticationEntryPoint(jwtAuthEntryPoint)
                )
                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> {


                        auth
                                .requestMatchers("/api/v1/auth/**").permitAll();

                                if(environment.acceptsProfiles(Profiles.of("dev"))){
                                    auth
                                            .requestMatchers("/swagger-ui/**","/swagger-ui.html",
                                                    "/v3/api-docs/**","/v3/api-docs.yaml",
                                                    "/swagger-resources/**", "/webjars/**").permitAll();
                                           // .requestMatchers("/h2-console/**").permitAll()
                                }


                              auth
                                .anyRequest().authenticated();
                }
        );
//                .headers(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /*
    * AuthenticationManager es la interfaz principal de Spring Security para procesar autenticaciones.
    * Es como el "policía" que verifica las credenciales de los usuarios.
    *
    * @Bean:
        Registra este método como un bean de Spring
        El objeto retornado estará disponible en el contenedor de Spring

    * AuthenticationConfiguration:
        Es una clase de Spring Security que contiene la configuración de autenticación
        Spring la inyecta automáticamente como parámetro
     getAuthenticationManager():
        Obtiene el AuthenticationManager configurado automáticamente por Spring Security
        Este manager ya viene con toda la configuración estándar (UserDetailsService, password encoder, etc.)
        *
        * 1. Toma el username → lo pasa a UserDetailsService
        2. UserDetailsService busca en BD → trae el usuario
        3. Toma el password plano → lo pasa a PasswordEncoder
        4. PasswordEncoder compara con el hash de BD
        5. Si OK: Crea Authentication object con roles
           Si falla: Lanza BadCredentialsException
    * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "http://localhost:4200"
        ));
        corsConfiguration.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept"
        ));
        //Configuramos que se exponga el header de autorizacion para que el frontend pueda usarlo para extraer el token por ejemplo
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }
}
