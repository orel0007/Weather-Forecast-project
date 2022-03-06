package main;
import main.repo.MassageRepository;
import main.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication
public class Application {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public MassageRepository msgRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
