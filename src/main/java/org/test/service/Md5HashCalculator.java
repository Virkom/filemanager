package org.test.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Service to generate hash sum of file by MD5 algorithm
 */
public class Md5HashCalculator implements HashCalculator {

    @Override
    public String getHash(File file) throws IOException {
        String md5Hash;
        try (InputStream is = Files.newInputStream(file.toPath())) {
            md5Hash = DigestUtils.md5Hex(is);
        }

        return md5Hash;
    }
}
