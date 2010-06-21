package groovymag.services

class JobQueueService {
    boolean transactional = true
    static expose = ['jms']
	static destination = "queue.jobs"

    JobDispatchService jobDispatchService

    def onMessage = { message ->
      def obj = new GroovyShell().evaluate(message.job)
      jobDispatchService.dispatch(obj)
    }

}
