package com.sekarre.chatdemo.security.jwt;

import com.sekarre.chatdemo.config.ProfilesHolder;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.repositories.UserRepository;
import com.sekarre.chatdemo.security.HttpParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Profile(ProfilesHolder.NO_AUTH_DISABLED)
@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String SPLIT_REGEX = " ";
    public static final String BEARER = "Bearer ";
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String parameter = request.getParameter(HttpParameters.TOKEN);
        final String token;
        boolean isHeaderTypeAuth = true;
        if (isEmpty(header) || !header.startsWith(BEARER)) {
            if (isEmpty(parameter) || !parameter.startsWith(BEARER)) {
                chain.doFilter(request, response);
                return;
            }
            isHeaderTypeAuth = false;
        }

        token = isHeaderTypeAuth ? header.split(SPLIT_REGEX)[1].trim() : parameter.split(SPLIT_REGEX)[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        User user = userRepository.findByUsername(jwtTokenUtil.getUsername(token))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}