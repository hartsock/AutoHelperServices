package groovymag.services

import groovymag.jobs.*
import grails.test.GrailsUnitTestCase

public class JobDispatchServiceTests extends GrailsUnitTestCase {
  JobDispatchService jobDispatchService

  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testDependencyInjection() {
    assert jobDispatchService != null
  }

  void testDuckDispatch() {
    def job = jobDispatchService.dispatch(new Duck())
    println job
    assert job != null
    assert job.completed == true
    assert job.errorMessage == null
  }

  void testRabbitDispatch() {
    def job = jobDispatchService.dispatch(new Rabbit())
    println job
    assert job != null
    assert job.completed == true
    assert job.errorMessage == null
  }

  void testManDispatch() {
    def job = jobDispatchService.dispatch(new Man())
    println job
    assert job != null
    assert job.completed == true
    assert job.errorMessage == null
  }

  void testSuperDispatch() {
    def job = jobDispatchService.dispatch(new SuperMan())
    println job
    assert job != null
    assert job.completed == true
    assert job.errorMessage == null
  }
}