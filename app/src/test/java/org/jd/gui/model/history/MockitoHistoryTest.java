package org.jd.gui.model.history;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class MockitoHistoryTest {
    @Test
    public void testVerify() throws URISyntaxException {
        // mock creation
        History history = new History();
        History spyHistory = spy(history);

        // mock object
        spyHistory.add(new URI("/User/Test/framework.jar"));
        spyHistory.add(new URI("/User/Test/interface.jar"));
        spyHistory.add(new URI("/User/Test/method.jar"));

        // stubbing
        when(spyHistory.forward()).thenReturn(new URI("/User/Test/forward.jar"));

        Assert.assertEquals(2, spyHistory.backward.size());
        Assert.assertEquals(0, spyHistory.forward.size());

        // print real backward() element
        System.out.println(spyHistory.backward());
        System.out.println(spyHistory.backward());

        Assert.assertEquals(2, spyHistory.forward.size());

        // print stubbed method
        System.out.println(spyHistory.forward());

        // verification
        verify(spyHistory).add(new URI("/User/Test/framework.jar"));
    }
}
