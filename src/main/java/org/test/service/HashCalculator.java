package org.test.service;

import java.io.File;
import java.io.IOException;

/**
 * Service to generate hash sum of file
 */
public interface HashCalculator {
    String getHash(File file) throws IOException;
}
