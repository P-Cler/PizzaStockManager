package org.serratec.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 @Autowired
	    private JwtAuthFilter jwtAuthFilter;

//	 @Bean
//	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	     http
//	         .csrf(csrf -> csrf.disable())
//	         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//	         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//	         .authorizeHttpRequests(auth -> auth
//	             .requestMatchers(HttpMethod.POST, "/professores").permitAll() 
//	             .requestMatchers("/auth/login").permitAll()                
//	             .requestMatchers("/h2-console/**").permitAll()             
//
//	             .requestMatchers("/alunos/**").hasRole("PROFESSOR")         
//	             .requestMatchers("/jogos/**").hasRole("PROFESSOR")          
//
//	             .anyRequest().authenticated()
//	         )
//	         .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//	         
//	     http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
//
//	     return http.build();
//	 }
	 
	 //Desenvolvimento
	 
	 @Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		    http
		        .csrf(csrf -> csrf.disable())
		        .cors() 
		        .and()
		        .headers(headers -> headers.frameOptions().disable())
		        .authorizeHttpRequests(auth -> auth
		            .requestMatchers("/h2-console/**").permitAll()
		            .anyRequest().permitAll()
		        );
		    return http.build();
		}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.addAllowedOrigin("http://localhost:5173");
	    configuration.addAllowedOrigin("https://pizzastockmanager.onrender.com"); 
	    configuration.addAllowedOrigin("https://pizza-stock-manager.vercel.app"); 
	    configuration.addAllowedMethod("*");
	    configuration.addAllowedHeader("*");
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
}