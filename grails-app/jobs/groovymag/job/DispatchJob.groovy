package groovymag.job

import groovymag.jobs.Job
import groovymag.services.JobDispatchService


class DispatchJob {
    def timeout = 5000l // execute job once in 5 seconds
    JobDispatchService jobDispatchService

    def execute() {
        Job.findAllByCompleted(false).each { job ->
          jobDispatchService.dispatch(job).save()
        }
    }
}
