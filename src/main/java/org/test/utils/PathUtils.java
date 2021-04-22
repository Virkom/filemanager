package org.test.utils;

/**
 * Utility file with paths to endpoints and directories. Using static variables is not good practice but
 * they can be used for small example application I think.
 */
public class PathUtils {

    public static final String FILE_ID_PLACEHOLDER = "ref";
    public static final String FILE_FORM_DATA_PARAM = "file";

    public static final String FILE_UPLOAD_PATH = "/files/";
    public static final String FILE_DOWNLOAD_PATH = "/files/content/{" + FILE_ID_PLACEHOLDER + "}";
    public static final String FILE_DELETE_PATH = "/files/content/{" + FILE_ID_PLACEHOLDER + "}";

    public static final String PATH_TO_FILES = "uploads/";
}
