package login.Register.loginRegister.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.Register.loginRegister.security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//JWT Validation and Authentication Setup
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;



        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.replace("Bearer ", "").trim();  //  Completely remove "Bearer "
            logger.info("Final Clean JWT (Without Bearer): {}", jwt);
        }

    // Ensure that only the JWT token is passed to JwtHelper
        if (jwt != null && !jwt.isEmpty()) {
            try {
                 username = jwtHelper.extractUsername(jwt); // Now correctly passing only the token
                logger.info("Extracted Username from JWT: {}", username);
            } catch (Exception e) {
                logger.error("JWT Parsing Error: {}", e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtHelper.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("JWT validation failed!");
            }
        }
        filterChain.doFilter(request, response);  // Finally, forwards the request to the next controller.
    }
}

//        Incoming HTTP Request
//              ↓
//        [JwtRequestFilter]
//              ↓
//        Check if Authorization header has JWT
//              ↓
//        Extract JWT, parse username
//              ↓
//        Validate JWT & load user
//              ↓
//        If valid, set authentication in SecurityContext
//              ↓
//        Forward to Controller
//
//        If JWT missing or invalid:
//              ↓
//        Spring Security → [JwtAuthenticationEntryPoint]
//              ↓
//        Returns 401 Unauthorized + custom message