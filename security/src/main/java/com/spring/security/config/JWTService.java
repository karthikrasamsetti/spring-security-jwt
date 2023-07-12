package com.spring.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@Slf4j
public class JWTService {
    private static final String SECRET_KEY="07b0bfddf38ad23713b537d75d9557d9caa220b3300324115e15b5c872d9ef2b";
    public String extractUsername(String token) {
        log.info("enter extract username");
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        log.info("enter extractClaim");
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    //    generating the token
    public String generateToken(UserDetails userDetails){
        log.info("enter generateToken");
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(Map<String,Object> extractClaims,
                                UserDetails userDetails){
        log.info("enter createToken");
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    //    validation of token
    public boolean isTokenValid(String token,UserDetails userDetails){
        log.info("enter isTokenValid");
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        log.info("enter isTokenExpired");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.info("enter extractExpiration");
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        log.info("enter extractAllClaims");
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        log.info("enter getSignInKey");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
