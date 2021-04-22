package org.test.service;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.test.model.FileInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileInfoLocalStorageTest {

    private final FileInfoStorage fileInfoStorage = new FileInfoLocalStorage();

    @Test
    void saveNewFileInfo() {
        String hash = "b05403212c66bdc8ccc597fedf6cd5fe";
        String ref = UUID.randomUUID().toString();
        String filename = ref + ".txt";
        FileInfo fileInfo = new FileInfo(hash, ref, filename, 101010);
        FileInfo result = fileInfoStorage.addFileInfo(fileInfo);

        assertNull(result);
    }

    @Test
    void saveExistingFileInfo() {
        String hash = "b05403212c66bdc8ccc597fedf6cd5fe";
        String ref = UUID.randomUUID().toString();
        String filename = ref + ".txt";
        FileInfo fileInfo = new FileInfo(hash, ref, filename, 101010);
        FileInfo result = fileInfoStorage.addFileInfo(fileInfo);

        assertNull(result);

        result = fileInfoStorage.addFileInfo(fileInfo);

        assertEquals(fileInfo, result);
    }

    @Test
    void findByRef() {
        String hash = "b05403212c66bdc8ccc597fedf6cd5fe";
        String ref = UUID.randomUUID().toString();
        String filename = ref + ".txt";
        FileInfo fileInfo = new FileInfo(hash, ref, filename, 101010);
        fileInfoStorage.addFileInfo(fileInfo);

        Optional<FileInfo> result = fileInfoStorage.findByRef(ref);
        assertTrue(result.isPresent());
        assertEquals(hash, result.get().getHash());
    }

    @Test
    void deleteFile() {
        String hash = "b05403212c66bdc8ccc597fedf6cd5fe";
        String ref = UUID.randomUUID().toString();
        String filename = ref + ".txt";
        FileInfo fileInfo = new FileInfo(hash, ref, filename, 101010);
        fileInfoStorage.addFileInfo(fileInfo);

        fileInfoStorage.deleteByHash(hash);

        Optional<FileInfo> result = fileInfoStorage.findByRef(ref);

        assertFalse(result.isPresent());
    }

}
