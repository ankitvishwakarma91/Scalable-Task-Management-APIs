package com.softworkshub.internassignment.config;

import com.softworkshub.internassignment.entity.Users;
import com.softworkshub.internassignment.exception.JwtTokenNotProvidedException;
import com.softworkshub.internassignment.repository.UserRepository;
import com.softworkshub.internassignment.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(final JwtService jwtService, final UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String path = request.getServletPath();
        if (path.startsWith("/api/v1/auth") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }


        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            String json = """
                    {
                        "message": "JWT Token Not Provided",
                        "status": 401,
                        "timestamp": %d,
                        "path": "%s"
                    }
                    """.formatted(System.currentTimeMillis(), request.getRequestURI());

            response.getWriter().write(json);
            return;

        } else {


//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
            try {
                String token = authHeader.substring(7);
                String userEmail = jwtService.extractUsername(token);

                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    Users user = userRepository.findByEmail(userEmail)
                            .orElseThrow(() -> new RuntimeException("User Not Found"));

                    if (jwtService.isTokenValid(token, user)) {

                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        List.of(new SimpleGrantedAuthority(user.getRole().name()))
                                );

                        authenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }

            } catch (Exception ex) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                String json = """
                        {
                            "message": "Invalid or Expired JWT Token",
                            "status": 401,
                            "timestamp": %d,
                            "path": "%s"
                        }
                        """.formatted(System.currentTimeMillis(), request.getRequestURI());

                response.getWriter().write(json);
                return;
            }
            filterChain.doFilter(request, response);
        }
    }
}
