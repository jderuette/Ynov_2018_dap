package fr.ynov.dap.client.dto.in;

import java.util.Date;

import com.google.gson.Gson;

/**
 * Represent exception from server side.
 * @author Kévin Sibué
 *
 */
public class ExceptionInDto {

    /**
     * Creation date of current exception.
     */
    private Date creationDate;

    /**
     * Store error description.
     */
    private String errorDescription;

    /**
     * Default constructor. Auto fill creationDate.
     * @param edVal Exception's description
     */
    public ExceptionInDto(final String edVal) {
        this.creationDate = new Date();
        this.errorDescription = edVal;
    }

    /**
     * Default constructor.
     * @param ex Exception Thrown exception
     */
    public ExceptionInDto(final Exception ex) {
        this(ex.getMessage());
    }

    /**
     * Default constructor.
     */
    public ExceptionInDto() {

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
     * Transform json string to current class instance.
     * @param json String representation of current class
     * @return New ExceptionInDto object
     */
    public static ExceptionInDto fromJSON(final String json) {

        Gson gson = new Gson();

        return gson.fromJson(json, ExceptionInDto.class);

    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

}
