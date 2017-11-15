package cs3219.assignment5.CIRApp.CIRApp;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ParserTest extends TestCase {
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ParserTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ParserTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws IOException 
     */
    public void testApp() throws IOException
    {
    	Parser parser = new Parser(); 
    	String userInput = "for conferences ArXiv, ICSE, give authors in each of the years 2000-2016";
    	String dataLoc = "";
    	parser.parseInput(userInput, dataLoc);
    	
        assertEquals("authors", parser.getInputObj().getQueryType());
        assertEquals("2000", parser.getInputObj().getNumYrs().get(0).toString());
    }
}
