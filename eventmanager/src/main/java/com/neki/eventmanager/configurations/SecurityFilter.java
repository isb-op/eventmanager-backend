package com.neki.eventmanager.configurations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.neki.eventmanager.repositories.UserRepository;
import com.neki.eventmanager.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserRepository userRepository; 
	
	@Override
	 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        if (token != null) {
            String login = tokenService.validateToken(token);

            if (login != null && !login.isEmpty()) {
                UserDetails userDetails = userRepository.findByLogin(login);
                
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Usuário não encontrado.");
                    return;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
			
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		var token = this.recoverToken(request);
//		if (token != null) {
//			var login = tokenService.validateToken(token);
//			UserDetails userDetails = userRepository.findByLogin(login);
//			var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//		}
//		filterChain.doFilter(request, response);
//	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null)
			return null;
		return authHeader.replace("Bearer", "").trim();
	}
}

