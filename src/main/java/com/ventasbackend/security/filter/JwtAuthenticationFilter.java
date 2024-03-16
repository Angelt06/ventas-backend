package com.ventasbackend.security.filter;

import com.ventasbackend.entity.User;
import com.ventasbackend.repository.UserRepository;
import com.ventasbackend.service.serviceimpl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Obtenemos la authorización que tiene el Jwt desde el Header
        String authHeader = request.getHeader("Authorization"); //Bearer Jwt

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //Sacar el Jwt separando el Bearer
        String jwt = authHeader.split( " ")[1];

        //Extraer el username del jwt, extraemos todos los claims del payload con el método extractAllClams, y luego extraemos el subject.
        String username = jwtService.extractAllClaims(jwt).getSubject();

        //Setear un objeto autenticación dentro del secuity context
        User user = userRepository.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);



    }
}
