package org.serratec.backend.config;

import java.io.IOException;

import org.serratec.backend.entity.Usuario;
import org.serratec.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                      HttpServletResponse response,
                                      FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                // Lidar com token inválido/expirado, se necessário
                logger.error("Não foi possível extrair o username do token", e);
            }
        }

        // Se temos um username e o usuário ainda não está autenticado no contexto atual
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Carrega o usuário do banco de dados. A entidade Usuario já implementa UserDetails!
            Usuario usuario = this.usuarioRepository.findByEmail(username).orElse(null);

            // Valida o token com base nos dados do usuário encontrado
            if (usuario != null && jwtUtil.isTokenValid(token, usuario)) {
                
                // ✅ PONTO CRÍTICO DA CORREÇÃO ✅
                // Cria o token de autenticação com as permissões REAIS do usuário
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        usuario, // O principal agora é o objeto Usuario completo
                        null,
                        usuario.getAuthorities() // <<-- ESSA LINHA CARREGA AS ROLES PARA O SPRING
                );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Coloca o usuário autenticado no contexto de segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}