package lvat.login01.security.non_oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

        for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
            logger.error(e.nextElement());
        }
        logger.error(request.getMethod());

        logger.error("Unauthorized error", authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
    }
}
