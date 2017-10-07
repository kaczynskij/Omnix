package prv.kaczynskij;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import prv.kaczynskij.config.Configs;

public class ConfigsTest extends TestCase {

    @Before
    public void setUp() throws Exception {
	System.setProperty("omnix.env", "test");
    }

    @Test
    public void testSystemExist() {
	assertNotNull(Configs.systemProperties());
    }

    @Test
    public void testPropertiesExist() {
	assertNotNull(Configs.appProperties());
    }

    @Test
    public void testAppPropertyGreeting() {
	assertEquals("Hello World!", Configs.appProperties().getString("greeting"));
    }

}
