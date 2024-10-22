package com.example.api_gateway.utils;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    public void validateToken(final String token) {
        System.out.println(3.5);
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException expEx) {
            System.out.println("Token expired" + expEx);
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt" + unsEx);
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt" + mjEx);
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature" + sEx);
        } catch (Exception e) {
            System.out.println("invalid token" + e);
        }
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }
}