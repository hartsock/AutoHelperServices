package groovymag.workers

/**
 * classes that implement the Worker interface know how to operate on a specific
 * "job" class. "Job" classes are message classes that carry instructions for what
 * type of work needs to be performed.
 */
public interface Worker {
  Class getJobClass()
  def doWork(job)
}
