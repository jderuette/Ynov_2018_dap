package fr.ynov.dap.dto.out;

import java.util.Date;

/**
 * Model used when an exception thrown.
 * @author Kévin Sibué
 *
 */
public class ExceptionOutDto {

    /**
     * Creation date of current exception.
     */
    private Date creationDate;

    /**
     * Store error description.
     */
    private String errorDescription;

    /**
     * Default constructor.
     * Auto fill creation date.
     * @param edVal Exception's description
     */
    public ExceptionOutDto(final String edVal) {
        this.creationDate = new Date();
        this.errorDescription = edVal;
    }

    /**
     * Default constructor.
     * @param ex Exception
     */
    public ExceptionOutDto(final Exception ex) {
        this(ex.getMessage());
    }

    /**
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param val the errorDescription to set
     */
    public void setErrorDescription(final String val) {
        this.errorDescription = val;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

}
