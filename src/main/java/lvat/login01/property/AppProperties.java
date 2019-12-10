package lvat.login01.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }

    public static class Auth {
        private String accessTokenSecret;
        private String refreshTokenSecret;
        private long accessTokenExpirationSec;
        private long refreshTokenExpirationSec;

        public String getAccessTokenSecret() {
            return accessTokenSecret;
        }

        public void setAccessTokenSecret(String accessTokenSecret) {
            this.accessTokenSecret = accessTokenSecret;
        }

        public long getAccessTokenExpirationSec() {
            return accessTokenExpirationSec;
        }

        public void setAccessTokenExpirationSec(long accessTokenExpirationSec) {
            this.accessTokenExpirationSec = accessTokenExpirationSec;
        }

        public String getRefreshTokenSecret() {
            return refreshTokenSecret;
        }

        public void setRefreshTokenSecret(String refreshTokenSecret) {
            this.refreshTokenSecret = refreshTokenSecret;
        }

        public long getRefreshTokenExpirationSec() {
            return refreshTokenExpirationSec;
        }

        public void setRefreshTokenExpirationSec(long refreshTokenExpirationSec) {
            this.refreshTokenExpirationSec = refreshTokenExpirationSec;
        }
    }

    public static final class OAuth2 {

        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
