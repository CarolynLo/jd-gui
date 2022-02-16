package org.jd.gui.model.history;

import junit.framework.TestCase;
import org.junit.Assert;
import java.net.URI;
import java.net.URISyntaxException;

public class NewHistoryTest extends TestCase {
    public void testCurrentFileUri() throws URISyntaxException {
        History history  = new History();
        // Current history will be null at the beginning
        Assert.assertNull(history.current);

        // Add the first uri
        URI uriA = new URI("/User/Test/framework.jar");
        history.add(uriA);
        Assert.assertEquals(uriA, history.current);
        Assert.assertFalse(history.canBackward()); // no backward
        Assert.assertFalse(history.canForward()); // no forward

        // Add the second uri after the first uri
        URI uriB = new URI("/User/Test/Four.class");
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
        URI uriC = new URI("/User/Test/Five.class");
        history.add(uriC);
        Assert.assertEquals(uriC, history.current);
        Assert.assertFalse(history.canForward()); // no forward
        Assert.assertTrue(history.canBackward()); // can backward
        Assert.assertEquals(uriA, history.backward()); // backward get the previous uri
        Assert.assertTrue(history.canForward()); // can forward after go back to the first uri
        Assert.assertEquals(uriC, history.forward()); // forward get the next uri

        /*
         * If just open a compress data file, the uri will be added to the history.
         * But if navigate/search/open the file in compress data files,
         * it will only add the file instead of the compress data file
         */

        // Navigate specific file in compress data file
        history.backward();
        Assert.assertEquals(uriA, history.current);
        URI uriD = new URI("/User/Test/framework.jar#Framework");
        history.add(uriD);
        Assert.assertEquals(uriD, history.current);
        Assert.assertFalse(history.canBackward()); // no backward because the previous uri is its compress data file
        // Do nothing if not navigating a specific file
        history.add(uriA);
        Assert.assertNotEquals(uriA, history.current);
        Assert.assertEquals(uriD, history.current);
        // Navigate another specific file in same compressed data file
        URI uriE = new URI("/User/Test/framework.jar#Main");
        history.add(uriE);
        Assert.assertEquals(uriE, history.current);
        Assert.assertFalse(history.canForward()); // no forward
        Assert.assertTrue(history.canBackward()); // can backward
        Assert.assertEquals(uriD, history.backward()); // backward get the previous uri
        Assert.assertTrue(history.canForward()); // can forward after go back to the first uri
        Assert.assertEquals(uriE, history.forward()); // forward get the next uri

        // Search specific file in compress data file
        URI uriF = new URI("/User/Test/interface.jar");
        history.add(uriF);
        Assert.assertEquals(uriF, history.current);
        URI uriG = new URI("/User/Test/interface.jar?IWord");
        history.add(uriG);
        Assert.assertEquals(uriG, history.current);
        // Do nothing if not searching a specific file
        history.add(uriF);
        Assert.assertNotEquals(uriF, history.current);
        Assert.assertEquals(uriG, history.current);
        // Search another specific file in same compressed data file
        URI uriH = new URI("/User/Test/interface.jar?IFrequency");
        history.add(uriH);
        Assert.assertEquals(uriH, history.current);
        Assert.assertFalse(history.canForward()); // no forward
        Assert.assertTrue(history.canBackward()); // can backward
        Assert.assertEquals(uriG, history.backward()); // backward get the previous uri
        Assert.assertTrue(history.canForward()); // can forward after go back to the first uri
        Assert.assertEquals(uriH, history.forward()); // forward get the next uri

        // Open specific file in compress data file
        URI uriI = new URI("/User/Test/example.jar");
        history.add(uriI);
        Assert.assertEquals(uriI, history.current);
        URI uriJ = new URI("/User/Test/example.jar!/main.class");
        history.add(uriJ);
        Assert.assertEquals(uriJ, history.current);
        Assert.assertNotEquals(uriI, history.backward()); // the compress data file will not be store to backward list
    }
}
