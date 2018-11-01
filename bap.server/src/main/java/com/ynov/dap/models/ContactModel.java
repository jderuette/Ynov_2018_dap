package com.ynov.dap.models;

/**
 * MODEL CONTACT.
 * @author POL
 */
public class ContactModel {

    /** The amount. */
    //TODO Bap by Djer "amount" se traduit par "montant", je sait que les relations ont de la valeur mais quand même !
    private Integer amount;


    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }


    /**
     * Sets the amount.
     *
     * @param inAmount the new amount
     */
    public void setAmount(final Integer inAmount) {
       this.amount = inAmount;
    }


    /**
     * Instantiates a new contact model.
     *
     * @param inAmount the in amount
     */
    public ContactModel(final Integer inAmount) {
       this.setAmount(inAmount);
    }

}
