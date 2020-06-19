package com.koen.server.party;

import com.koen.server.party.config.WebConfig;
import com.koen.server.party.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
@Order(1)
@SpringBootApplication
public class AplicationInit implements WebApplicationInitializer {

    private String TMP_FOLDER = "/tmp"; //
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; //
    private final static String DISPATCHER = "dispatcher";
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(WebConfig.class);
        webApplicationContext.register(WebSecurityConfig.class);
        servletContext.addListener(new ContextLoaderListener(webApplicationContext));
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(DISPATCHER, new DispatcherServlet(webApplicationContext));
        servletRegistration.addMapping("/");
        servletRegistration.setLoadOnStartup(1);
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER,
                MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2); //

        servletRegistration.setMultipartConfig(multipartConfigElement); //
    }
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10000000);
        return multipartResolver;
    }
    public static void main(String[] args) {
        SpringApplication.run(AplicationInit.class, args);
    }

}
