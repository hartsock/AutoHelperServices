package groovymag.workers

import groovymag.jobs.SuperMan

class SuperManService implements Worker {
  boolean transactional = true

  def Class getJobClass() {
    return SuperMan
  }

  def doWork( job) {
    println job.message
    return job
  }
}
