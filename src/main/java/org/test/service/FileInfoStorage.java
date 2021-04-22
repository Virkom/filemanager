package org.test.service;

import java.util.Optional;

import org.test.model.FileInfo;

/**
 * Storage to save file info
 * For one instance of application it's possible, but if it will be highloaded system with many instances
 * need to add separate implementation using AWS, database, Redis, separate microservice, etc.
 */
public interface FileInfoStorage {
    /**
     * Save file info
     * @param fileInfo object with file info
     * @return saved file info
     */
    FileInfo addFileInfo(FileInfo fileInfo);

    /**
     * Find stored file info by ref
     * @param ref file reference
     * @return optional file info
     */
    Optional<FileInfo> findByRef(String ref);

    /**
     * Delete file info by file hash
     * @param hash file hash (like MD5 or other)
     */
    void deleteByHash(String hash);
}
