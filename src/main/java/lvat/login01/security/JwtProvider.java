package lvat.login01.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private String jwtSecret = "_levananhtu2019";
    private long jwtExpiration = 100000;
    private Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    public String generateJwtToken(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return Jwts.builder()
                .setIssuedAt((new Date()))
                .setSubject(customUser.getUsername())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String authToken) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();
    }

    public boolean isValidJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token");
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token");
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty");
        }
        return false;
    }
}
