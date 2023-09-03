package com.example.Plowithme.config;

import com.example.Plowithme.exception.handler.JwtAccessDeniedHandler;
import com.example.Plowithme.security.JwtAuthEntryPoint;
import com.example.Plowithme.security.JwtAuthticationFilter;
import com.example.Plowithme.security.JwtExceptionFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@AllArgsConstructor
@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthEntryPoint authenticationEntryPoint;

    private JwtAuthticationFilter authenticationFilter;

    private JwtExceptionFilter jwtExceptionFilter;
    @Qualifier("customAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;



//    //ADMIN 계정 설정 - 일단 시큐리티 유저로 설정
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        userDetailsManager.createUser(admin);
//        return userDetailsManager;
//    }


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
          //      .cors(cors -> cors.disable())
                //.cors(cors -> );
                .cors(cors -> corsConfigurationSource())
                .csrf(AbstractHttpConfigurer::disable)
                        .exceptionHandling( exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        //.accessDeniedHandler(new JwtAccessDeniedHandler())
                )

                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize

                                .requestMatchers(HttpMethod.GET, "/board/**").permitAll()
                                .requestMatchers("/test/**").permitAll()
                                .requestMatchers("/class").permitAll()
                                .requestMatchers("/class/**").permitAll()
                                .requestMatchers("/auth/login", "/auth/**").permitAll()

                                .requestMatchers("/C:/**").permitAll()

                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**","/swagger-ui/**").permitAll()
                                .anyRequest().authenticated()


                ).sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .exceptionHandling( exception -> exception
//                        .authenticationEntryPoint(authenticationEntryPoint)
//                        //.accessDeniedHandler(new JwtAccessDeniedHandler())
//                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)

//                .addFilterBefore(jwtExceptionFilter, JwtAuthticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://43.200.172.177:8080");
        config.addAllowedOrigin("http://localhost:3000");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails ramesh = User.builder()
//                .username("ramesh")
//                .password(passwordEncoder().encode("ramesh"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(ramesh, admin);
//    }
//
//         .authenticationProvider(authenticationProvider)
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//            .logout((logout) ->
//            //logout.deleteCookies("remove")
//            //.invalidateHttpSession(false)
//            logout.logoutUrl("/api/v1/auth/logout")
//            // .logoutSuccessUrl("/logout-success")
//            .addLogoutHandler(logoutHandler)
//                            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//            )


    public UserDetailsService userDetailsService() {
        //인메모리에 username, password, role 설정
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("pwd")
                        .roles("USER")
                        .build();

        System.out.println("password : " + user.getPassword());

        return new InMemoryUserDetailsManager(user);
    }


}
