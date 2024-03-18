<<<<<<< HEAD
package grupoLem.appGestion.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    public String generateAccesToken(String username){
       return Jwts.builder()
               .setSubject(username)
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
               .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
               .compact();
    }

    public boolean isTokenValid(String token){
    try{
         Jwts.parserBuilder()
                 .setSigningKey(getSignatureKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
         return true;
    }catch (Exception e){
        log.error("Token invalido, error: ".concat(e.getMessage()));
         return false;
      }
    }

    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    //Esta funcion es para obtener un solo claim
    public<T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims= extractAllClaims(token);
        System.out.println("Claims " +claims);
        return claimsTFunction.apply(claims);
    }

    public Claims extractAllClaims(String token){
      return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        // Obtener el nombre de usuario del UserDetails
        String username = userDetails.getUsername();

        // Definir las claims del token
        Claims claims = Jwts.claims().setSubject(username);

        // Definir la fecha de emisión y de expiración del token
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Long.parseLong(timeExpiration));

        // Construir el token JWT
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
=======
package grupoLem.appGestion.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    public String generateAccesToken(String username){
       return Jwts.builder()
               .setSubject(username)
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
               .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
               .compact();
    }

    public boolean isTokenValid(String token){
    try{
         Jwts.parserBuilder()
                 .setSigningKey(getSignatureKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
         return true;
    }catch (Exception e){
        log.error("Token invalido, error: ".concat(e.getMessage()));
         return false;
      }
    }

    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    //Esta funcion es para obtener un solo claim
    public<T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims= extractAllClaims(token);
        System.out.println("Claims " +claims);
        return claimsTFunction.apply(claims);
    }

    public Claims extractAllClaims(String token){
      return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        // Obtener el nombre de usuario del UserDetails
        String username = userDetails.getUsername();

        // Definir las claims del token
        Claims claims = Jwts.claims().setSubject(username);

        // Definir la fecha de emisión y de expiración del token
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Long.parseLong(timeExpiration));

        // Construir el token JWT
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
