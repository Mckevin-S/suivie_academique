package com.suivie_academique.suivie_academique.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // ✅ Lecture depuis application.properties
    @Value("${jwt.secret:MonSuperSecretJWTKeyQuiFaitAuMoins32Caracteres123456789}")
    private String SECRET;

    @Value("${jwt.expiration:86400000}")
    private long EXPIRATION; // 24h par défaut

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /**
     * Extrait le username du token JWT.
     */
    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expiré", e);
        } catch (JwtException e) {
            throw new RuntimeException("Token invalide", e);
        }
    }

    /**
     * Extrait la date d'expiration du token.
     */
    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    /**
     * Vérifie si le token est expiré.
     */
    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Génère un token JWT pour l'utilisateur.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valide le token : vérifie le username ET l'expiration.
     */
    public boolean validateToken(String token, UserDetails user) {
        try {
            String username = extractUsername(token);
            boolean usernameMatch = username.equals(user.getUsername());
            boolean notExpired = !isTokenExpired(token);

            return usernameMatch && notExpired;
        } catch (Exception e) {
            return false;
        }


    }

    public boolean validateToken(String token) {
             try {
                  Jwts.parserBuilder()
                          .setSigningKey(SECRET)
                                         .build()
                                         .parseClaimsJws(token);

                 return true;
            } catch (JwtException e) {
                  return false;
              }
          }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    /**
     * package com.suivie_academique.suivie_academique.security;
     *
     * import io.jsonwebtoken.*;
     * import io.jsonwebtoken.security.Keys;
     * import org.springframework.beans.factory.annotation.Value;
     * import org.springframework.stereotype.Component;
     *
     * import javax.crypto.SecretKey;
     * import java.util.Date;
     *
     * @Component
     * public class JwtUtils {
     *
     *     private final SecretKey secretKey;
     *     private final long expiration;
     *
     *     public JwtUtils(
     *             @Value("${jwt.secret}") String secret,
     *             @Value("${jwt.expiration}") long expiration
     *     ) {
     *         this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
     *         this.expiration = expiration;
     *     }
     *
     *     public String generateToken(String username) {
     *         Date now = new Date();
     *         Date expiry = new Date(now.getTime() + expiration);
     *
     *         return Jwts.builder()
     *                 .setSubject(username)
     *                 .setIssuedAt(now)
     *                 .setExpiration(expiry)
     *                 .signWith(secretKey, SignatureAlgorithm.HS256)
     *                 .compact();
     *     }
     *
     *     public boolean validateToken(String token) {
     *         try {
     *             Jwts.parserBuilder()
     *                     .setSigningKey(secretKey)
     *                     .build()
     *                     .parseClaimsJws(token);
     *
     *             return true;
     *         } catch (JwtException e) {
     *             return false;
     *         }
     *     }
     *
     *     public String getUsernameFromToken(String token) {
     *         return Jwts.parserBuilder()
     *                 .setSigningKey(secretKey)
     *                 .build()
     *                 .parseClaimsJws(token)
     *                 .getBody()
     *                 .getSubject();
     *     }
     * }
     */


}