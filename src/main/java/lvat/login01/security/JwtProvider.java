package lvat.login01.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    private String jwtAccessTokenSecret = "_20LevananhtuAccess19";
    private String jwtRefreshTokenSecret = "_20RefreshLevananhtu19";
    private long jwtAccessTokenExpiration = 1_800;//s -> 30m
    private long jwtRefreshTokenExpiration = 2_592_000;//s -> 30d
    private Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    public String generateJwtAccessToken(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", customUser.getUsername());
        claims.put("roles", customUser.getAuthorities());
        return Jwts.builder()
                .setIssuedAt((new Date()))
                .setClaims(claims)
                .setExpiration(new Date((new Date()).getTime() + jwtAccessTokenExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtAccessTokenSecret)
                .compact();
    }

    public String generateJwtRefreshToken(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", customUser.getUsername());
        claims.put("roles", customUser.getAuthorities());
        return Jwts.builder()
                .setIssuedAt((new Date()))
                .setExpiration(new Date((new Date()).getTime() + jwtRefreshTokenExpiration * 1000))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshTokenSecret)
                .compact();
    }

    /**
     * @param authentication
     * @return [0]: access token, [1]: refresh token
     *
     */
    public String[] generateTokenPair(Authentication authentication) {
        String[] result = new String[2];
        result[0] = generateJwtAccessToken(authentication);
        result[1] = generateJwtRefreshToken(authentication);
        return result;
    }

    public String getUsernameFromJwtToken(String authToken) {
        return Jwts.parser()
                .setSigningKey(jwtAccessTokenSecret)
                .parseClaimsJws(authToken)
                .getBody()
                .get("username", String.class);
    }

    public boolean isValidJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtAccessTokenSecret).parseClaimsJws(authToken);
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
