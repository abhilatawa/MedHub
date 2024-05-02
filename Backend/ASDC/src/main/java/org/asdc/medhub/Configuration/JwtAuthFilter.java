package org.asdc.medhub.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.asdc.medhub.Service.Interface.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Jwt token authentication filter
 * Each request goes through this and validates the user
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    /**
     * JWT service instance
     */
    private final IJwtService jwtService;

    /**
     * User detail service instance
     */
    private final UserDetailsService userDetailsService;

    /**
     * Parameterized constructor
     * @param jwtService - jwt service injection
     * @param userDetailsService - user detail service injection
     */
    @Autowired
    public JwtAuthFilter(IJwtService jwtService,UserDetailsService userDetailsService){
        this.jwtService=jwtService;
        this.userDetailsService=userDetailsService;
    }

    //region PROTECTED METHOD
    /**
     * Filter actual logic
     * @param request - incoming request instance
     * @param response - outgoing response instance
     * @param filterChain - filter chain instance
     * @throws ServletException - exception
     * @throws IOException - exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        String username =this.jwtService.extractUsername(token);
        String userId= this.jwtService.extractUserId(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails user =userDetailsService.loadUserByUsername(username);
            if(jwtService.isValid(token, user,userId)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    //endregion
}
