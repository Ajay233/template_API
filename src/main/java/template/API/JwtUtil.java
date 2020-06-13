package template.API;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "${spring.datasource.secretKey}";

    private String createJwt(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateJwt(UserDetails userDetails){
        HashMap<String, Object> claims = new HashMap<>();
        return createJwt(claims, userDetails);
    }

    private Claims extractAllClaims(String jwt){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String jwt){
        return extractClaim(jwt, Claims::getSubject);
    }

    public Date extractExpiration(String jwt){
        return extractClaim(jwt, Claims::getExpiration);
    }

    public Boolean isJwtExpired(String jwt){
        return extractExpiration(jwt).before(new Date());
    }

    public Boolean isJwtValid(String jwt, UserDetails userDetails){
        String username = extractUsername(jwt);
        return (userDetails.getUsername().equals(username) && !isJwtExpired(jwt));
    }

}
