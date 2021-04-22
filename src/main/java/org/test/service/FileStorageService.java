package org.test.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.test.model.FileInfo;

/**
 * Service to manage storage operations
 * For one instance of application it's possible, but if it will be highloaded system with many instances
 * need to add separate implementation using Amazon S3 or separate microservice
 */
public interface FileStorageService {
    /**
     * Save file if not exists
     * @param tmpFile uploaded file (from form-data parameters)
     * @param contentType content type of uploaded file
     * @param contentDisposition content disposition of uploaded file
     * @return response json as string
     * @throws IOException
     */
    String saveFile(File tmpFile, String contentType, String contentDisposition) throws IOException;

    /**
     * Get stored file info by file reference
     * @param ref file reference
     * @return file info from storage
     */
    Optional<FileInfo> getFileInfoByRef(String ref);

    /**
     * Delete file info from storage and file from the filesystem
     * @param ref file reference
     * @return result as string (if exception was thrown)
     */
    String deleteFile(String ref);

    /**
     * Method to fill file info storage about files in upload directory before application was started
     */
    void fillStorageOnApplicationStart();
}
