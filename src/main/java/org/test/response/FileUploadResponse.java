package org.test.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Response if file was uploaded successfully
 * Converts to json string before send to user
 */
public class FileUploadResponse {

    private final String ref;
    private final String content_type;
    private final long size_bytes;

    public FileUploadResponse(String ref, String content_type, long size_bytes) {
        this.ref = ref;
        this.content_type = content_type;
        this.size_bytes = size_bytes;
    }

    public String getRef() {
        return ref;
    }

    public String getContent_type() {
        return content_type;
    }

    public long getSize_bytes() {
        return size_bytes;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
