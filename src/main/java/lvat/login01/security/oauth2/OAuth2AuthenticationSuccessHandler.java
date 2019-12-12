package lvat.login01.security.oauth2;

import lvat.login01.exception.BadRequestException;
import lvat.login01.property.AppProperties;
import lvat.login01.security.non_oauth2.JwtProvider;
import lvat.login01.security.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final AppProperties appProperties;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    public OAuth2AuthenticationSuccessHandler(JwtProvider jwtProvider, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository, AppProperties appProperties) {
        this.jwtProvider = jwtProvider;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.appProperties = appProperties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            //LOGGER.info("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAMETER_COOKIE_NAME)
                .map(Cookie::getValue);
        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }
        String targetUrl = redirectUri.orElse("/");
        String token = jwtProvider.generateJwtAccessToken(authentication);
        return UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        //        return appProperties.getOauth2().getAuthorizedRedirectUris()
        //                .stream()
        //                .anyMatch(authorizedRedirectUri -> {
        //                    // Only validate host and port. Let the clients use different paths if they want to
        //                    URI authorizedURI = URI.create(authorizedRedirectUri);
        //                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
        //                            && authorizedURI.getPort() == clientRedirectUri.getPort();
        //                });
        URI authorizedRedirectUri = URI.create(appProperties.getOauth2().getAuthorizedRedirectUri());
        return clientRedirectUri.getPort() == authorizedRedirectUri.getPort() && clientRedirectUri.getHost().equalsIgnoreCase(authorizedRedirectUri.getHost());

//        return appProperties
//                .getOauth2()
//                .getAuthorizedRedirectUris()
//                .stream()
//                .anyMatch(authorizedRedirectUri -> {
//                    URI authorizedUri = URI.create(authorizedRedirectUri);
//                    return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//                            && authorizedUri.getPort() == clientRedirectUri.getPort();
//                });
    }
}
