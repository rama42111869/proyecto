package com.portfolio.mgb.Security.jwt;

import com.portfolio.mgb.Security.Service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger logger= LoggerFactory.getLogger(JwtEntryPoint.class);

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try{
        String token=getToken(request);
        if(token !=null && jwtProvider.validateToken(token)){
            String nombreUsuario=jwtProvider.getNombreUsuarioFromToken(token);
            UserDetails userDetails=userDetailsServiceImpl.loadUserByUsername(nombreUsuario);
            UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
    catch(Exception e){
        logger.error("Fallo el metodo do FilterInternal");
    }
    filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer"))
            return header.replace("Bearer","");
        return null;
    }

}