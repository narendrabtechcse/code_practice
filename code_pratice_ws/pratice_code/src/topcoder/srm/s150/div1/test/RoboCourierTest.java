package topcoder.srm.s150.div1.test; 

import java.util.Scanner;
import java.io.PrintWriter;
import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.*;
import topcoder.srm.s150.div1.*;  

/** 
* RoboCourier Tester. 
* 
* @author <Premkumar Bhaskal> 
* @since <pre>Jun 11, 2013</pre> 
* @version 1.0 
*/ 
public class RoboCourierTest { 
    
    RoboCourier testClass = new RoboCourier();
    long starttime;
    
    @Before
    public void before() throws Exception {
          starttime = System.nanoTime(); 
    } 
    
    @After
    public void after() throws Exception {
		long now = System.nanoTime();
		System.out.println("elapsed time " + (now-starttime)/1000000 + "milli secs");
    } 
    
        /** 
    * 
    * Method: timeToDeliver(String[] path) 
    * 
    */ 
    @Test
    public void testTimeToDeliver() throws Exception { 
        String[] path;

		path = new String[]{"FRRFLLFLLFRRFLF"};
		testClass.timeToDeliver(path);
    } 
    
        
} 
