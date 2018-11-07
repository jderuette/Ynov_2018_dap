package fr.ynov.dap.utils;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.http.GenericUrl;

/**
 * Every methods which can help with Url.
 * @author Kévin Sibué
 *
 */
public class UrlUtils {

    /**
     * Build a current host (and port) absolute URL.
     * @param req         The current HTTP request to extract schema, host, port
     *                    informations
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    public static String buildRedirectUri(final HttpServletRequest req, final String destination) {

        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);

        return url.build();

    }

}
