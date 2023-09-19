package com.mssecurity.mssecurity.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mssecurity.mssecurity.interceptors.SecurityInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) { //propio de spring, se necesita activar varios interceptores
        registry.addInterceptor(securityInterceptor)//se necesita que se active un interceptor
            .addPathPatterns("/api/**") //cuando pase por cualquier ruta
            .excludePathPatterns("/api/public/**"); //excluyendo la ruta security
            // Asegúrarse de que las rutassean las correctas
    }

}
