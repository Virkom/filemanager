package org.test.service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.test.model.FileInfo;

/**
 * Local file storage
 */
public class FileInfoLocalStorage implements FileInfoStorage {

    private final ConcurrentHashMap<String, FileInfo> storage = new ConcurrentHashMap<>();

    @Override
    public FileInfo addFileInfo(FileInfo fileInfo) {
        return storage.putIfAbsent(fileInfo.getHash(), fileInfo);
    }

    @Override
    public Optional<FileInfo> findByRef(String ref) {
        return storage.values().stream()
                .filter(item -> ref.equals(item.getRef()))
                .findFirst();
    }

    @Override
    public void deleteByHash(String hash) {
        storage.remove(hash);
    }
}
