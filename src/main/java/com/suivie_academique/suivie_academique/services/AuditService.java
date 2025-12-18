package com.suivie_academique.suivie_academique.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service de journalisation d'audit pour tracer les actions des utilisateurs.
 */
@Service
public class AuditService {

    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT_LOGGER");
    private static final Logger securityLogger = LoggerFactory.getLogger("SECURITY_LOGGER");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Récupère l'utilisateur actuellement connecté.
     */
    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return "ANONYMOUS";
    }

    /**
     * Log une action d'audit générale.
     */
    public void logAction(String action, String entityType, String entityId, String details) {
        String username = getCurrentUsername();
        String timestamp = LocalDateTime.now().format(formatter);

        auditLogger.info("[AUDIT] User: {} | Action: {} | Entity: {} | ID: {} | Details: {} | Timestamp: {}",
                username, action, entityType, entityId, details, timestamp);
    }

    /**
     * Log une création d'entité.
     */
    public void logCreate(String entityType, String entityId, String details) {
        logAction("CREATE", entityType, entityId, details);
    }

    /**
     * Log une modification d'entité.
     */
    public void logUpdate(String entityType, String entityId, String details) {
        logAction("UPDATE", entityType, entityId, details);
    }

    /**
     * Log une suppression d'entité.
     */
    public void logDelete(String entityType, String entityId) {
        logAction("DELETE", entityType, entityId, "Entity deleted");
    }

    /**
     * Log une lecture/consultation d'entité.
     */
    public void logRead(String entityType, String entityId) {
        logAction("READ", entityType, entityId, "Entity accessed");
    }

    /**
     * Log une tentative de connexion réussie.
     */
    public void logLoginSuccess(String username, String ipAddress) {
        String timestamp = LocalDateTime.now().format(formatter);
        securityLogger.info("[LOGIN_SUCCESS] User: {} | IP: {} | Timestamp: {}",
                username, ipAddress, timestamp);
    }

    /**
     * Log une tentative de connexion échouée.
     */
    public void logLoginFailure(String username, String ipAddress, String reason) {
        String timestamp = LocalDateTime.now().format(formatter);
        securityLogger.warn("[LOGIN_FAILURE] User: {} | IP: {} | Reason: {} | Timestamp: {}",
                username, ipAddress, reason, timestamp);
    }

    /**
     * Log une déconnexion.
     */
    public void logLogout(String username) {
        String timestamp = LocalDateTime.now().format(formatter);
        securityLogger.info("[LOGOUT] User: {} | Timestamp: {}", username, timestamp);
    }

    /**
     * Log un accès refusé.
     */
    public void logAccessDenied(String username, String resource, String action) {
        String timestamp = LocalDateTime.now().format(formatter);
        securityLogger.warn("[ACCESS_DENIED] User: {} | Resource: {} | Action: {} | Timestamp: {}",
                username, resource, action, timestamp);
    }

    /**
     * Log un changement de mot de passe.
     */
    public void logPasswordChange(String username) {
        String timestamp = LocalDateTime.now().format(formatter);
        securityLogger.info("[PASSWORD_CHANGE] User: {} | Timestamp: {}", username, timestamp);
    }

    /**
     * Log une erreur de sécurité.
     */
    public void logSecurityError(String username, String errorType, String details) {
        String timestamp = LocalDateTime.now().format(formatter);
        securityLogger.error("[SECURITY_ERROR] User: {} | Error: {} | Details: {} | Timestamp: {}",
                username, errorType, details, timestamp);
    }
}
