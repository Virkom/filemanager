package org.test.service;

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
}
