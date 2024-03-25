
package grupoLem.appGestion.security;

import grupoLem.appGestion.security.filters.JwtAuthenticationFilter;
import grupoLem.appGestion.security.filters.JwtAuthorizationFilter;
import grupoLem.appGestion.security.jwt.JwtUtils;
import grupoLem.appGestion.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthorizationFilter authorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                            AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/login","/createUser","/", "/error")
                        .permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/login","/createClient").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/Huespedes","/huesped/{idHost}","/reservas",
                            "/buscarReserva/{idReservations}","/habitaciones","/buscarHabitacion/{idRoom}",
                                    "/comienzoReserva/{startDate}","/finalizaReserva/{endDate}"
                                    ,"/habitacionesDisponibles","/acompaniantes",
                                    "/acompaniante/{idCompanion}","/mediosDePago", "medioDePago/{idMediaPayment}",
                                    "/habitaciones/estado-horario")
                            .hasAnyRole("ADMIN","EMPLOYEE");
                    auth.requestMatchers(HttpMethod.POST, "/crearHuesped", "/nuevaReserva",
                                    "/NuevaHabitacion","/crearAcompaniante","medioDePagoReserva/{idReservation}")
                            .hasAnyRole("ADMIN","EMPLOYEE");
                    auth.requestMatchers(HttpMethod.PUT, "/actualizarHuesped/{idHost}","/editarReserva/{idReservations}",
                                    "/editarHabitacion/{idRoom}","/actualizarAcompaniante/{idCompanion}","editarMedioPago/{idMediaPayment}")
                            .hasAnyRole("ADMIN","EMPLOYEE");
                    auth.requestMatchers(HttpMethod.DELETE, "/eliminarAcompaniante/{idCompanion}").hasAnyRole("ADMIN","EMPLOYEE");
                    auth.requestMatchers(HttpMethod.DELETE, "/eliminarHuesped/{idHost}","/eliminarReserva/{idReservations}",
                                    "/eliminarHabitacion/{idRoom}","eliminarMedioPago/{idMediaPayment}")
                            .hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session ->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                )
                .build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

}
