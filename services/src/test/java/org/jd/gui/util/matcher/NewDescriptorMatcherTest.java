package org.jd.gui.util.matcher;

import junit.framework.TestCase;
import org.junit.Assert;

public class NewDescriptorMatcherTest extends TestCase {
    public void testMatchMethodDescriptors() {
        // When both parameters start with "(" and have ")"
        Assert.assertTrue(DescriptorMatcher.matchMethodDescriptors("()", "()"));
        Assert.assertTrue(DescriptorMatcher.matchMethodDescriptors("(Test)", "(Test)"));

        // When both parameters start with "(" but one of it don't have ")"
        Assert.assertFalse(DescriptorMatcher.matchMethodDescriptors("(Test)", "(Test"));
        Assert.assertFalse(DescriptorMatcher.matchMethodDescriptors("(Test", "(Test)"));

        // When both parameters start with "(" and one of it comes with "*"
        Assert.assertTrue(DescriptorMatcher.matchMethodDescriptors("(*Test", "(*Test"));
        Assert.assertTrue(DescriptorMatcher.matchMethodDescriptors("(*Test", "(Test"));
        Assert.assertTrue(DescriptorMatcher.matchMethodDescriptors("(Test", "(*Test"));

        // When one or both parameters don't have "(" and ")"
        Assert.assertFalse(DescriptorMatcher.matchMethodDescriptors("Test", "Test"));
        Assert.assertFalse(DescriptorMatcher.matchMethodDescriptors("(Test)", "Test"));
        Assert.assertFalse(DescriptorMatcher.matchMethodDescriptors("Test", "(Test)"));
    }
}
