package org.jd.gui.util.io;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewLineOutputStreamTest extends TestCase{
    public void testInitializeLineSeparator(){
        try {
            NewlineOutputStream newlineOutputStream = new NewlineOutputStream(Files.newOutputStream(Paths.get(new File("src/test/files/Four.class").toURI())));
            Assert.assertEquals(newlineOutputStream.getLineSeparator()[0], (int)'\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
