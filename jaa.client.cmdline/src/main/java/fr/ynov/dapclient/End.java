package fr.ynov.dapclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Used by the Event object.
 * @author adrij
 *
 */
public class End {
	//TODO jaa by Djer attributs devraient être privé, avec acesseurs

    /**
     * Datetime.
     */
    @SerializedName("dateTime")
    @Expose
    public DateTime dateTime;
}
