package com.budget_app.configs;

import com.budget_app.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    //инжект. для сверки токена с юзером из бд на каждый запрос
    //private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        String login = null;
        String jwt = null;
        if (authHeader!=null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                login = jwtTokenUtil.getLoginFromToken(jwt);
            }catch (ExpiredJwtException e) {
                log.debug("The token is expired");
            }
        }

        if (login !=null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //запрос для сверки токена с юзером из бд
//            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(token);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, null, jwtTokenUtil.getRoles(jwt)
                    .stream()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }
}
