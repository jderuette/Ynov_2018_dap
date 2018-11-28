package fr.ynov.dap.model;

/**
 * @author Mon_PC
 */
public class EventModel {

    /**.
     * Propriété organisateur
     */
    private String organisateur;
    /**.
     * Propriété sujet
     */
    private String sujet;
    /**.
     * Propriété dateDebut
     */
    private String dateDebut;

    /**.
     * Propriété dateFin
     */
    private String dateFin;

    /**
     * @return organisateur
     */
    public String getOrganisateur() {
        return organisateur;
    }

    /**.
     * @param newOrganisateur nouveau organisateur
     */
    public void setOrganisateur(final String newOrganisateur) {
        this.organisateur = newOrganisateur;
    }

    /**
     * @return sujet
     */
    public String getSujet() {
        return sujet;
    }

    /**.
     * @param newSujet nouveau sujet
     */
    public void setSujet(final String newSujet) {
        this.sujet = newSujet;
    }

    /**
     * @return dateDebut
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * @param newDateDebut nouvelle dateDebut
     */
    public void setDateDebut(final String newDateDebut) {
        this.dateDebut = newDateDebut;
    }

    /**
     * @return dateFin
     */
    public String getDateFin() {
        return dateFin;
    }

    /**.
     * @param newDateFin nouvelle dateFin
     */
    public void setDateFin(final String newDateFin) {
        this.dateFin = newDateFin;
    }
}
