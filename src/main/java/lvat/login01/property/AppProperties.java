package lvat.login01.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
        private static Logger LOGGER = LoggerFactory.getLogger(Auth.class);
        private String accessTokenSecret;
        private String refreshTokenSecret;
        private long accessTokenExpirationSec;
        private long refreshTokenExpirationSec;

        public String getAccessTokenSecret() {
            LOGGER.info(accessTokenSecret);
            return accessTokenSecret;
        }

        public void setAccessTokenSecret(String accessTokenSecret) {
            LOGGER.info(accessTokenSecret);
            this.accessTokenSecret = accessTokenSecret;
        }

        public long getAccessTokenExpirationSec() {
            LOGGER.info(String.valueOf(accessTokenExpirationSec));
            return accessTokenExpirationSec;
        }

        public void setAccessTokenExpirationSec(long accessTokenExpirationSec) {
            LOGGER.info(String.valueOf(accessTokenExpirationSec));
            this.accessTokenExpirationSec = accessTokenExpirationSec;
        }

        public String getRefreshTokenSecret() {
            LOGGER.info(refreshTokenSecret);
            return refreshTokenSecret;
        }

        public void setRefreshTokenSecret(String refreshTokenSecret) {
            LOGGER.info(refreshTokenSecret);
            this.refreshTokenSecret = refreshTokenSecret;
        }

        public long getRefreshTokenExpirationSec() {
            LOGGER.info(String.valueOf(refreshTokenExpirationSec));
            return refreshTokenExpirationSec;
        }

        public void setRefreshTokenExpirationSec(long refreshTokenExpirationSec) {
            LOGGER.info(String.valueOf(refreshTokenExpirationSec));
            this.refreshTokenExpirationSec = refreshTokenExpirationSec;
        }
    }

    public static final class OAuth2 {
        private static Logger LOGGER = LoggerFactory.getLogger(OAuth2.class);

        //        private List<String> authorizedRedirectUris = new ArrayList<>();
        private String authorizedRedirectUri = "";

//        public List<String> getAuthorizedRedirectUris() {
//            return authorizedRedirectUris;
//        }

//        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
//            this.authorizedRedirectUris = authorizedRedirectUris;
//            return this;
//        }

        public String getAuthorizedRedirectUri() {
            LOGGER.info(authorizedRedirectUri);
            return authorizedRedirectUri;
        }

        public void setAuthorizedRedirectUri(String authorizedRedirectUri) {
            LOGGER.info(authorizedRedirectUri);
            this.authorizedRedirectUri = authorizedRedirectUri;
        }
    }
}
