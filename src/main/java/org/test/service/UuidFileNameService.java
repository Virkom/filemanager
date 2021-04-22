package org.test.service;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

/**
 * Service to manipulate with filenames.
 * Reference is UUID
 */
public class UuidFileNameService implements FileNameService {

    @Override
    public String generateNameByContentDisposition(String contentDisposition) {
        String fileName = "default";
        Pattern p = Pattern.compile(".+filename=\"(.+?)\".*");

        if (Objects.nonNull(contentDisposition)) {
            Matcher m = p.matcher(contentDisposition);
            if (m.find()) {
                fileName = m.group(1);
            }
        }

        String extension = FilenameUtils.getExtension(fileName);
        String ref = UUID.randomUUID().toString();

        if (extension.isEmpty()) {
            return ref;
        }

        return ref + "." + extension;
    }
}
