package fr.ynov.dap.dap.utils;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.http.GenericUrl;

/**
 * The Class BuildRedirectUri.
 */
public class BuildRedirectUri {

	/**
	 * Builds the redirect uri.
	 *
	 * @param req
	 *            the req
	 * @param destination
	 *            the destination
	 * @return the string
	 */
	public String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}
}
