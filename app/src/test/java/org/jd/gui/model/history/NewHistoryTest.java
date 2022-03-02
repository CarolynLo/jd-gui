package org.jd.gui.model.history;

import junit.framework.TestCase;
import org.junit.Assert;
import java.net.URI;
import java.net.URISyntaxException;

public class NewHistoryTest extends TestCase {
    public void testCurrentFileUri() throws URISyntaxException {
        History history  = new History();
        // Current history will be null at the beginning

        System.out.println("0 test");
        Assert.assertNull(history.current);

        // Add the first uri
        URI uriA = new URI("/User/Test/framework.jar");

        System.out.println("1 test");
        history.add(uriA);
        Assert.assertEquals(uriA, history.current);
        Assert.assertFalse(history.canBackward()); // no backward
        Assert.assertFalse(history.canForward()); // no forward

        // Add the second uri after the first uri
        URI uriB = new URI("/User/Test/Four.class");

        System.out.println("2 test");
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

        System.out.println("3 test");
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

        System.out.println("4 test");
        history.add(uriD);
        Assert.assertEquals(uriD, history.current);
        Assert.assertFalse(history.canBackward()); // no backward because the previous uri is its compress data file
        // Do nothing if not navigating a specific file

        System.out.println("5 test");
        history.add(uriA);
        Assert.assertNotEquals(uriA, history.current);

        Assert.assertEquals(uriD, history.current);
        // Navigate another specific file in same compressed data file
        URI uriE = new URI("/User/Test/framework.jar#Main");

        System.out.println("6 test");
        history.add(uriE);
        Assert.assertEquals(uriE, history.current);
        Assert.assertFalse(history.canForward()); // no forward
        Assert.assertTrue(history.canBackward()); // can backward
        Assert.assertEquals(uriD, history.backward()); // backward get the previous uri
        Assert.assertTrue(history.canForward()); // can forward after go back to the first uri
        Assert.assertEquals(uriE, history.forward()); // forward get the next uri

        // Search specific file in compress data file
        URI uriF = new URI("/User/Test/interface.jar");

        System.out.println("7 test");
        history.add(uriF);
        Assert.assertEquals(uriF, history.current);
        URI uriG = new URI("/User/Test/interface.jar?IWord");

        System.out.println("8 test");
        history.add(uriG);
        Assert.assertEquals(uriG, history.current);
        // Do nothing if not searching a specific file

        System.out.println("9 test");
        history.add(uriF);
        Assert.assertNotEquals(uriF, history.current);
        Assert.assertEquals(uriG, history.current);
        // Search another specific file in same compressed data file
        URI uriH = new URI("/User/Test/interface.jar?IFrequency");

        System.out.println("10 test");
        history.add(uriH);
        Assert.assertEquals(uriH, history.current);
        Assert.assertFalse(history.canForward()); // no forward
        Assert.assertTrue(history.canBackward()); // can backward
        Assert.assertEquals(uriG, history.backward()); // backward get the previous uri
        Assert.assertTrue(history.canForward()); // can forward after go back to the first uri
        Assert.assertEquals(uriH, history.forward()); // forward get the next uri

        // Open specific file in compress data file
        URI uriI = new URI("/User/Test/example.jar");

        System.out.println("11 test");
        history.add(uriI);
        Assert.assertEquals(uriI, history.current);
        URI uriJ = new URI("/User/Test/example.jar!/main.class");

        System.out.println("12 test");
        history.add(uriJ);
        Assert.assertEquals(uriJ, history.current);
        Assert.assertNotEquals(uriI, history.backward()); // the compress data file will not be store to backward list
    }
}
