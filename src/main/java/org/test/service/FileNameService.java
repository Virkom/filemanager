package org.test.service;

import java.io.FileNotFoundException;

/**
 * Service to manipulate with filenames
 */
public interface FileNameService {
    /**
     * Gets original filename extension from content disposition header,
     * generates new reference to file and returns new reference with original extension
     * For example,
     *      input: "attachment;filename=test_filename.jpg"
     *      output: "5bc9d29b-1185-4ff6-bfc0-8eeabfbd82d9.jpg"
     * @param contentDisposition header
     * @return new filename (generated hash with original extension)
     */
    String generateNameByContentDisposition(String contentDisposition);

    /**
     * Provides reference without extension by filename
     * For example,
     *      input: "5bc9d29b-1185-4ff6-bfc0-8eeabfbd82d9.png"
     *      output: "5bc9d29b-1185-4ff6-bfc0-8eeabfbd82d9"
     * @param filename with extension
     * @return generated filename without extension
     */
    String getRefByFilename(String filename);

    /**
     * Provides filename (from filesystem) by generated reference
     * For example,
     *      input: "5bc9d29b-1185-4ff6-bfc0-8eeabfbd82d9"
     *      output: "5bc9d29b-1185-4ff6-bfc0-8eeabfbd82d9.png"
     * @param ref reference
     * @return filename with extension
     * @throws FileNotFoundException if file not found in storage
     */
    String getFileNameByRef(String ref) throws FileNotFoundException;
}
