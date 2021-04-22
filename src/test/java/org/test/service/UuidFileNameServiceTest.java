package org.test.service;

import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UuidFileNameServiceTest {

    private final FileNameService fileNameService = new UuidFileNameService();

    @Test
    void generateNameByContentDisposition() {
        String filename = fileNameService.generateNameByContentDisposition("attachment; filename=\"filename.jpg\"");
        UUID.fromString(FilenameUtils.getBaseName(filename));
        assertEquals("jpg", FilenameUtils.getExtension(filename));
    }
}
