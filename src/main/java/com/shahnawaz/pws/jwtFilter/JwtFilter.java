package com.shahnawaz.pws.jwtFilter;



import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shahnawaz.pws.security.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    MyUserDetailService myUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println("from jwtfilter for req "+request.getRequestURI());
        System.out.println(request.getHeader("lazyUpdate"));
        String authheader=request.getHeader("Authorization");
        Enumeration<String> lst=request.getHeaderNames();
        System.out.println(lst.toString());
        System.out.println("authheader is "+authheader);
        String token="";
        String username="";
        if( authheader !=null && authheader.startsWith("Bearer")) {
            System.out.println("from inside first if block");
            token=authheader.substring(7);

            username=jwtUtil.extractUsername(token);
        }
        System.out.println("username is "+username +"expression is "+username!=null);
        if(username!=null && username!=""  && SecurityContextHolder.getContext().getAuthentication()==null) {
            System.out.println("from inside second if block");
            System.out.println("username value is "+username);
            UserDetails uDetails=myUserDetailService.loadUserByUsername(username);
            if(jwtUtil.validateToken(token, uDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationTkenAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(uDetails,null, uDetails.getAuthorities());
                usernamePasswordAuthenticationTkenAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationTkenAuthenticationToken);
            }

        }

        System.out.println("b4 forwarding the request");

        filterChain.doFilter(request, response);

    }

}
