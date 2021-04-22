package org.test.response;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUploadResponseTest {

    @Test
    void toJson() throws JsonProcessingException {
        String ref = UUID.randomUUID().toString();
        FileUploadResponse fileUploadResponse = new FileUploadResponse(ref, "text/plain", 1010);
        assertEquals(
                "{\"ref\":\"" + ref + "\",\"content_type\":\"text/plain\",\"size_bytes\":1010}",
                fileUploadResponse.toJson()
        );
    }

}
