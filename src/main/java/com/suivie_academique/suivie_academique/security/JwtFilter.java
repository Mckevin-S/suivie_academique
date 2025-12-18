package com.suivie_academique.suivie_academique.security;

import com.suivie_academique.suivie_academique.services.implementations.PersonnelDetailsService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    // ✅ Déclarez tous les champs AVANT le constructeur
    private final JwtUtils jwtUtils;
    private final PersonnelDetailsService personnelDetailsService;

    public JwtFilter(JwtUtils jwtUtils, PersonnelDetailsService personnelDetailsService) {
        this.jwtUtils = jwtUtils;
        this.personnelDetailsService = personnelDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String header = req.getHeader("Authorization");
        String username = null;
        String token = null;

        // Extraction du token depuis le header Authorization
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtUtils.extractUsername(token);
            } catch (Exception e) {
                // Token invalide ou expiré : on laisse passer sans authentifier
                filterChain.doFilter(req, res);
                return;
            }
        }

        // Si username valide et aucune authentification en cours
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            var userDetails = personnelDetailsService.loadUserByUsername(username);

            // Valide le token (username + expiration)
            if (jwtUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(req, res);
    }
}