package com.suivie_academique.suivie_academique.security;

import com.suivie_academique.suivie_academique.services.implementations.LogActionServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ActionLoggingAspect {

    private final LogActionServiceImplementation logActionService;
    private final HttpServletRequest request;

    public ActionLoggingAspect(LogActionServiceImplementation logActionService, HttpServletRequest request) {
        this.logActionService = logActionService;
        this.request = request;
    }

    // 🔵 Avant l'appel d'un controller
    @Before("execution(* com.suivie_academique.suivie_academique.controllers..*(..))")
    public void logBeforeController(JoinPoint joinPoint) {

        String acteur = getActeur();
        String action = joinPoint.getSignature().getName();
        String url = request.getRequestURI();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();

        logActionService.log(
                acteur,
                "AVANT : " + action,
                "URL=" + url + " | Méthode=" + method + " | IP=" + ip
        );
    }

    // 🟢 Après succès (retour normal)
    @AfterReturning(pointcut = "execution(* com.suivie_academique.suivie_academique.controllers..*(..))", returning = "result")
    public void logAfterSuccess(JoinPoint joinPoint, Object result) {

        String acteur = getActeur();
        String action = joinPoint.getSignature().getName();
        String url = request.getRequestURI();

        logActionService.log(
                acteur,
                "SUCCÈS : " + action,
                "URL=" + url + " | Retour=" + (result != null ? result.toString() : "null")
        );
    }

    // 🔴 Après exception
    @AfterThrowing(pointcut = "execution(* com.suivie_academique.suivie_academique.controllers..*(..))", throwing = "ex")
    public void logAfterError(JoinPoint joinPoint, Throwable ex) {

        String acteur = getActeur();
        String action = joinPoint.getSignature().getName();
        String url = request.getRequestURI();

        logActionService.log(
                acteur,
                "ERREUR : " + action,
                "URL=" + url + " | Message=" + ex.getMessage()
        );
    }

    // 🟣 Récupération du nom de l'utilisateur connecté
    private String getActeur() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            return auth.getName();
        }
        return "ANONYME";
    }
}
