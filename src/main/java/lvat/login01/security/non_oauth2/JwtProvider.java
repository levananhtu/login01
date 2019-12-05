package lvat.login01.security.non_oauth2;

import io.jsonwebtoken.*;
import lvat.login01.security.CustomUser;
import lvat.login01.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static String jwtAccessTokenSecret = "_LevananhtuAccess00";
    private static String jwtRefreshTokenSecret = "_RefreshLevananhtu00";
    private static long jwtAccessTokenExpiration = 1_800;//s -> 30m
    private static long jwtRefreshTokenExpiration = 2_592_000;//s -> 30d
    private final UserService userService;
    private Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    public JwtProvider(UserService userService) {
        this.userService = userService;
    }

    public static String getJwtAccessTokenSecret() {
        return jwtAccessTokenSecret;
    }

    public static String getJwtRefreshTokenSecret() {
        return jwtRefreshTokenSecret;
    }

    private String generateToken(Authentication authentication, String secret, long expiration, SignatureAlgorithm algorithm) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(customUser.getUsername())
                .setIssuedAt((new Date()))
                .setExpiration(new Date((new Date()).getTime() + expiration * 1000))
                .signWith(algorithm, secret)
                .compact();
    }

    private String generateToken(String username, String secret, long expiration, SignatureAlgorithm algorithm) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt((new Date()))
                .setExpiration(new Date((new Date()).getTime() + expiration * 1000))
                .signWith(algorithm, secret)
                .compact();
    }

    public String generateJwtAccessToken(Authentication authentication) {
        return generateToken(authentication, jwtAccessTokenSecret, jwtAccessTokenExpiration, SignatureAlgorithm.HS512);
    }

    public String generateJwtAccessToken(String username) {
        return generateToken(username, jwtAccessTokenSecret, jwtAccessTokenExpiration, SignatureAlgorithm.HS512);
    }

    public String generateJwtRefreshToken(Authentication authentication) {
        return generateToken(authentication, jwtRefreshTokenSecret, jwtRefreshTokenExpiration, SignatureAlgorithm.HS256);
    }

    public String generateJwtRefreshToken(String username) {
        return generateToken(username, jwtRefreshTokenSecret, jwtRefreshTokenExpiration, SignatureAlgorithm.HS256);
    }

    public TokenPair generateTokenPair(Authentication authentication) {
        TokenPair tokenPair = new TokenPair();
        tokenPair.setAccessToken(generateJwtAccessToken(authentication));
        tokenPair.setRefreshToken(generateJwtRefreshToken(authentication));
        return tokenPair;
    }

    public TokenPair generateTokenPair(String refreshToken) throws JwtException {
        TokenPair tokenPair;
        try {
            tokenPair = new TokenPair();
            if (isValidRefreshToken(refreshToken)) {
                String username = getUsernameFromRefreshToken(refreshToken);
                if (!userService.existsByEmailIsOrUsernameIs(username)) {
                    throw new JwtException("username not found");
                }
                tokenPair.setAccessToken(generateJwtAccessToken(username));
                tokenPair.setRefreshToken(generateJwtRefreshToken(username));
            }
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token");
            throw e;
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token");
            throw e;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token");
            throw e;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature");
            throw e;
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty");
            throw e;
        }
        return tokenPair;
    }

    public String getUsernameFromAccessToken(String authToken) {
        return getUsernameFromToken(authToken, jwtAccessTokenSecret);
    }

    public String getUsernameFromRefreshToken(String authToken) {
        return getUsernameFromToken(authToken, jwtRefreshTokenSecret);
    }

    private String getUsernameFromToken(String authToken, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();
    }

    public boolean isValidAccessToken(String authToken) {
        return isValidToken(authToken, jwtAccessTokenSecret);
    }

    public boolean isValidRefreshToken(String authToken) {
        return isValidToken(authToken, jwtRefreshTokenSecret);
    }

    private boolean isValidToken(String authToken, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
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

    public class TokenPair {
        private String accessToken;
        private String refreshToken;

        public TokenPair() {
        }

        public TokenPair(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
