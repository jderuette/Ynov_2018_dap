package fr.ynov.dapclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * DateTime class used by Event.
 * @author adrij
 *
 */
public class DateTime {
	//TODO jaa by Djer attributs devraient être privé, avec acesseurs

    /**
     * TimeStamp Value.
     */
    @SerializedName("value")
    @Expose
    public Long value;

    /**
     * Is Date only.
     */
    @SerializedName("dateOnly")
    @Expose
    public Boolean dateOnly;

    /**
     * Time zone shift.
     */
    @SerializedName("timeZoneShift")
    @Expose
    public Integer timeZoneShift;

}
