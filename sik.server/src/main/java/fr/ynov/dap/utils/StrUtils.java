package fr.ynov.dap.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Wrapper to help with String object management.
 * @author Kévin Sibué
 *
 */
public final class StrUtils {

    /**
     * Default constructor.
     */
    private StrUtils() {

    }

    /**
     * Test if a String object is empty, blank or null.
     * @param val String object
     * @return True if it's empty, blank or null
     */
    public static Boolean isNullOrEmpty(final String val) {
        return StringUtils.isEmpty(val) || StringUtils.isBlank(val) || val == null;
    }

    /**
     * Transform string path with write system separator.
     * IMPORTANT ! Use "/" on your path.
     * @param val String path
     * @return New string path with every "/" replaced by file.separator
     */
    public static String resolvePath(final String val) {
        String result = "";
        String[] parts = val.split("/");
        for (int i = 0; i < parts.length; i++) {
            result += parts[i];
            if (i != parts.length - 1) {
                result += System.getProperty("file.separator");
            }
        }
        return result;
    }

}
