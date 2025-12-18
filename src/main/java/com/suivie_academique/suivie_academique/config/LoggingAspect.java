package com.suivie_academique.suivie_academique.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect pour logger automatiquement les appels de méthodes.
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Log l'entrée dans les méthodes des services.
     */
    @Before("execution(* com.suivie_academique.suivie_academique.services..*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        logger.debug("➤ Entrée dans {}.{} avec arguments: {}",
                className, methodName, Arrays.toString(args));
    }

    /**
     * Log la sortie des méthodes des services avec le résultat.
     */
    @AfterReturning(
            pointcut = "execution(* com.suivie_academique.suivie_academique.services..*(..))",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        logger.debug("✓ Sortie de {}.{} avec résultat: {}",
                className, methodName,
                result != null ? result.getClass().getSimpleName() : "null");
    }

    /**
     * Log les exceptions lancées par les services.
     */
    @AfterThrowing(
            pointcut = "execution(* com.suivie_academique.suivie_academique.services..*(..))",
            throwing = "exception"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        logger.error("✗ Exception dans {}.{} : {}",
                className, methodName, exception.getMessage(), exception);
    }

    /**
     * Log le temps d'exécution des méthodes de repository.
     */
    @Around("execution(* com.suivie_academique.suivie_academique.repositories..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        if (executionTime > 1000) {
            logger.warn("⏱ {}.{} a pris {} ms (LENT)", className, methodName, executionTime);
        } else {
            logger.debug("⏱ {}.{} a pris {} ms", className, methodName, executionTime);
        }

        return result;
    }
}
