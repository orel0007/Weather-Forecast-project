package main.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

/**
 * Client class: Singlton been for each Threat hold UserName, is_login
 */
@Component
public class Client implements Serializable {
    private String Username;
    private boolean is_login;

    public Client(){;}

    public void setUserName(String username) { this.Username = username;
   }
    public String getUserName() {return this.Username; }

    public void setLogin(boolean login) { this.is_login = login; }

    public boolean getLogin() {return this.is_login; }

    public void setData(String username){
        setUserName(username);
        setLogin(true);
    }

    /**
     * create client singlto for each thread
     * @return client
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Client ClientBean() {
        Client m = new Client();
        return m;
    }
}
