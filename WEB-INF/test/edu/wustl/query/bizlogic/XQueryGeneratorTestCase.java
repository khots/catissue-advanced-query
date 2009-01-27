package edu.wustl.query.bizlogic;
/**
 * 
 */

import junit.framework.TestCase;
import edu.wustl.cab2b.server.cache.EntityCache;
import edu.wustl.common.query.impl.PassOneXQueryGenerator;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.utility.Utility;

/**
 * TestCase class for XQueryGenerator.
 * 
 * @author ravindra_jain
 * @created 4th December, 2008
 */
public class XQueryGeneratorTestCase extends TestCase {

	public static PassOneXQueryGenerator xQueryGenerator = new PassOneXQueryGenerator();

    XQueryEntityManagerMock entityManager = new XQueryEntityManagerMock();
    
    static 
    {
           Logger.configure();
           try
           {
        	   /**
        	    * DS Initialization code here
        	    */
        	   Utility.initTest();
        	   EntityCache.getInstance();
        	    
	   	       /**
	   	         * Indicating - Do not LOG XQueries
	   	         */
	   	       Variables.isExecutingTCFramework = true;
	   	        
	   	       Variables.queryGeneratorClassName = "edu.wustl.common.query.impl.PassOneXQueryGenerator";
           }
           catch(Exception e)
           {
        	   e.printStackTrace();
           }
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
	    super.setUp();
    }

    
    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception
    {
	     super.tearDown();
    }

    
    public void testXQuery_Test1()
    {
    	// WRITE TEST CASE LOGIC HERE
    }

}