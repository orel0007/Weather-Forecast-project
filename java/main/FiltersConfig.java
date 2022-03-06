package main;

import main.beans.Client;
import main.filters.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Filter Configuration for catch all loggedIn pages with ClientBean parameter if user connect to the server or not.
 */
@EnableWebMvc
@Configuration
public class FiltersConfig implements WebMvcConfigurer {

    @Resource(name = "ClientBean")
    private Client c;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // if you want to apply filter only for REST controller: change the "/**" pattern
        registry.addInterceptor(new LoggingInterceptor(c)).addPathPatterns("/loggedIn/**");

    }
}