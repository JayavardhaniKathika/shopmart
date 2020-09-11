package com.project.shopmart.filters;

import com.project.shopmart.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    public boolean isTokenValid(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String token) throws IOException, ServletException {
        try{
            Claims claims= Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
                    .parseClaimsJws(token).getBody();
            httpRequest.setAttribute("userId",Integer.parseInt(claims.get("userId").toString()));
        }
        catch (Exception e) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
            return false;
        }
        return true;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse=(HttpServletResponse) servletResponse;

        String authHeader =httpRequest.getHeader("Authorization");
        if(authHeader!=null){
            String [] authHeaderArray=authHeader.split("Bearer ");
            if(authHeaderArray.length>1 && authHeaderArray[1]!=null){
                String token=authHeaderArray[1];
                if (!isTokenValid(httpRequest, httpResponse, token)) {
                    return;
                }
            }
            else{
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Authorization token must be Bearer [token]");
                return;
            }
        }



         else if (httpRequest.getCookies() != null) {
            Cookie[] cookies = httpRequest.getCookies();
//            for i in cookies

        }
        else{
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Authorization token must be provided");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
