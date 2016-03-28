package com.tilepay.bitreserveclient.model.transaction;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotBlank;

public class TransactionForm implements Serializable {

    private static final long serialVersionUID = 4578095061785400008L;

    private String id;

    @NotBlank
    private String destinationType;

    @NotBlank
    private String cardId;

    @NotBlank
    private String destination4Card;

    @NotBlank
    private String destination4Contact;

    @NotBlank
    private String destination4Address;

    @NotBlank
    @DecimalMin(value = "0.00000001")
    private String amount;

    @NotBlank
    private String currency;

    private String message;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the destinationType
     */
    public String getDestinationType() {
        return destinationType;
    }

    /**
     * @param destinationType
     *            the destinationType to set
     */
    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    /**
     * @return the cardId
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId
     *            the cardId to set
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * @return the destination4Card
     */
    public String getDestination4Card() {
        return destination4Card;
    }

    /**
     * @param destination4Card
     *            the destination4Card to set
     */
    public void setDestination4Card(String destination4Card) {
        this.destination4Card = destination4Card;
    }

    /**
     * @return the destination4Contact
     */
    public String getDestination4Contact() {
        return destination4Contact;
    }

    /**
     * @param destination4Contact
     *            the destination4Contact to set
     */
    public void setDestination4Contact(String destination4Contact) {
        this.destination4Contact = destination4Contact;
    }

    /**
     * @return the destination4Address
     */
    public String getDestination4Address() {
        return destination4Address;
    }

    /**
     * @param destination4Address
     *            the destination4Address to set
     */
    public void setDestination4Address(String destination4Address) {
        this.destination4Address = destination4Address;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
