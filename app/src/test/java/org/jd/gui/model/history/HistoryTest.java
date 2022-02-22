package org.jd.gui.model.history;

import junit.framework.TestCase;
import org.junit.Assert;
import java.io.File;
import java.net.URI;

public class HistoryTest extends TestCase {
    public void testCurrentFileUri(){
        History history  = new History();
        // Current history will be null at the beginning
        Assert.assertNull(history.current);

        // Add the first uri
        URI uriA = new File("src/test/files/framework.jar").toURI();
        history.add(uriA);
        Assert.assertEquals(uriA, history.current);
        Assert.assertFalse(history.canBackward()); // no backward
        Assert.assertFalse(history.canForward()); // no forward
        Assert.assertNull("This is not null", history.current);

        // Add the second uri after the first uri
        URI uriB = new File("src/test/files/Four.class").toURI();
        history.add(uriB);
        Assert.assertEquals(uriB, history.current);
        Assert.assertFalse(history.canForward()); // no forward
        Assert.assertTrue(history.canBackward()); // can backward
        Assert.assertEquals(uriA, history.backward()); // backward get the previous uri
        Assert.assertTrue(history.canForward()); // can forward after go back to the first uri
        Assert.assertEquals(uriB, history.forward()); // forward get the next uri

        // Add the third uri after the first uri, the second uri will be cleared
        history.backward();
        Assert.assertEquals(uriA, history.current);
        URI uriC = new File("src/test/files/Five.class").toURI();
        history.add(uriC);
        Assert.assertEquals(uriC, history.current);
        Assert.assertFalse(history.canForward()); // no forward
        Assert.assertTrue(history.canBackward()); // can backward
        Assert.assertEquals(uriA, history.backward()); // backward get the previous uri
        Assert.assertTrue(history.canForward()); // can forward after go back to the first uri
        Assert.assertEquals(uriC, history.forward()); // forward get the next uri
    }
}
