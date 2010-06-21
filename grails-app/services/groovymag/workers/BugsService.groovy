package groovymag.workers

import groovymag.jobs.Rabbit

class BugsService implements Worker {
  boolean transactional = true

  def Class getJobClass() {
    return Rabbit
  }

  def Object doWork(job) {
    println job.message
    return job
  }
}
