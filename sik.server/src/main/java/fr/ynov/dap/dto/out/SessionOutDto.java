package fr.ynov.dap.dto.out;

/**
 * Model that represent the status of a session.
 * @author Kévin Sibué
 *
 */
public class SessionOutDto {

    /**
     * Store session status.
     */
    private Boolean logged;

    /**
     * @return the logged
     */
    public Boolean getLogged() {
        return logged;
    }

    /**
     * @param val the logged to set
     */
    public void setLogged(Boolean val) {
        this.logged = val;
    }

}
