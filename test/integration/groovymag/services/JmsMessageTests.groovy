package groovymag.services

import grails.test.GrailsUnitTestCase
import groovymag.jobs.Duck
import org.springframework.jms.core.JmsTemplate

/**
 * Created by IntelliJ IDEA.
 * User: Shawn
 * Date: Jun 13, 2010
 * Time: 8:22:17 PM
 * To change this template use File | Settings | File Templates.
 */
class JmsMessageTests extends GrailsUnitTestCase {
  def jmsService
  // JmsTemplate jmsTemplate
  
  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testSendMessage() {
    jmsService.send("queue.jobs",[job:'new Duck()'])	 
  }


}