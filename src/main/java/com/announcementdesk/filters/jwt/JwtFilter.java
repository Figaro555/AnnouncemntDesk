package com.announcementdesk.filters.jwt;

import com.announcementdesk.domain.User;
import com.announcementdesk.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {

    private final String AUTHORIZATION = "Authorization";
    private UserService userService;
    private JwtManager jwtManager;

    public JwtFilter(UserService userService, JwtManager jwtManager){
        this.userService = userService;
        this.jwtManager = jwtManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("JWT Processing");
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtManager.validateToken(token)) {
            String username = jwtManager.getNameFromToken(token);
            User user = userService.findByName(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
