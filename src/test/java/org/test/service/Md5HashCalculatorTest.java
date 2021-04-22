package org.test.service;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Md5HashCalculatorTest {

    private final HashCalculator hashCalculator = new Md5HashCalculator();

    @Test
    void getHash() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("test.txt").getFile());
        assertEquals("b05403212c66bdc8ccc597fedf6cd5fe", hashCalculator.getHash(file));
    }
}
