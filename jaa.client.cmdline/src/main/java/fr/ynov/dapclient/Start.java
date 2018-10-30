package fr.ynov.dapclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Start used for the Event object.
 * @author adrij
 *
 */
public class Start {
	//TODO jaa by Djer attributs devraient être privé, avec acesseurs

    /**
     * Datetime.
     */
@SerializedName("dateTime")
@Expose
public DateTime dateTime;

}
