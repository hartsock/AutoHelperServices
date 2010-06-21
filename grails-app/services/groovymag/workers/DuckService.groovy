package groovymag.workers

import groovymag.jobs.Duck

class DuckService implements Worker {
  boolean transactional = true

  def Class getJobClass() { return Duck }

  def Object doWork(Object job) {
    println job.message
    return job
  }
}
