package main.filters;

import main.beans.Client;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoggingInterceptor filter: check all lo/loggedIn/** url if the user is connected or not,
 *  if user try enter pages with no permission its redriect to login page.
 */
public class LoggingInterceptor implements HandlerInterceptor{
    private Client clientInterceptor;
    public LoggingInterceptor(Client client) {
        clientInterceptor = client;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String str = request.getRequestURL().toString();
        if(clientInterceptor.getLogin() || str.equals("http://localhost:8080/"))
            return true;
        response.sendRedirect("/");
        return false;
    }
}
