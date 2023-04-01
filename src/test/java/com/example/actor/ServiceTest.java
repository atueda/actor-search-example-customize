package com.example.actor;


//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Service.
 */
public class ServiceTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ServiceTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ServiceTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    //@Test
    void testApp()
    {
        assertTrue( true );
    }
}
