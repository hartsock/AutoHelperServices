package groovymag.workers

import groovymag.jobs.Man

class BatmanService implements Worker {

  boolean transactional = true

  def Class getJobClass() {
    return Man
  }

  def Object doWork(Object job) {
    println job.message
    return job
  }
}
