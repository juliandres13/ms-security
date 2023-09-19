package com.mssecurity.mssecurity.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mssecurity.mssecurity.services.JWService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor { //implementa los metodos de esta interfaz
    
    @Autowired
    private JWService jwtService;

    private static final String BEARER_PREFIX = "Bearer "; // variable const. 

    //request es la carta con informacion (body) a enviar
    //response es el status
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { //devuelve un bool. true: deja pasar. false: no lo permite
        boolean success = true;
        String authorizationHeader = request.getHeader("Authorization"); //lee el paquete de authorizacion y lo guarda porque ahi es donde viene el token
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) { //se verifica que venga con authorization y al principio se le agrega BEARER_PREFIX
            String token = authorizationHeader.substring(BEARER_PREFIX.length()); //corta "Bearer " y solo se queda con el token
            // Verifica el token aquí, por ejemplo, con un servicio de autenticación
            // Si el token es válido, puedes permitir que la solicitud continúe
            // Si no es válido, puedes rechazar la solicitud o realizar otra acción
            // apropiada.
            // Por simplicidad, aquí solo se muestra cómo imprimir el token.
            System.out.println("Bearer Token: " + token); //imprime el token, aun no se ha validado
            success = jwtService.validateToken(token);
        } else {
            success = false;
        }
    // Devuelve true para permitir que la solicitud continúe o false para bloquearla
        return success;
    }

    // @Override
    // public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    //     // Lógica a ejecutar después de que se haya manejado la solicitud por el controlador
    // }

    // @Override
    // public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    //     // Lógica a ejecutar después de completar la solicitud, incluso después de la renderización de la vista
    // }

}
