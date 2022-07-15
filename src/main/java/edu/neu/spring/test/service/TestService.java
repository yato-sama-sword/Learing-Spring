package edu.neu.spring.test.service;


import java.util.logging.Logger;

/**
 * @author yato
 */
public class TestService {

    private Logger logger = Logger.getLogger("TestServer");

    public void testLogger(){
        logger.info("here is TestServer's logger");
    }

}
