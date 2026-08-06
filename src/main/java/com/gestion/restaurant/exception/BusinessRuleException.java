package com.gestion.restaurant.exception;

/**
 * Règle métier violée. {@code redirectUrl} permet de renvoyer l'utilisateur
 * vers le bon module après affichage du message flash.
 */
public class BusinessRuleException extends RuntimeException {

    private final String redirectUrl;

    public BusinessRuleException(String message) {
        this(message, null);
    }

    public BusinessRuleException(String message, String redirectUrl) {
        super(message);
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
