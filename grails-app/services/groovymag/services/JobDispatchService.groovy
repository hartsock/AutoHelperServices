package groovymag.services

import groovymag.workers.Worker
import grails.util.GrailsNameUtils
import org.codehaus.groovy.grails.commons.GrailsApplication

class JobDispatchService {

  static transactional = true
  GrailsApplication grailsApplication

  Map<Class,Worker> services

  /**
   * <b>must</b> be called in the BootStrap.groovy or else things blow up in dispatch
   */
  void init() {
    services = new HashMap<Class,Worker>()
    grailsApplication.getAllArtefacts().each { Class artefact ->
      final List interfaces = artefact.interfaces as List
      if(interfaces.contains(Worker.class)){
        def name = GrailsNameUtils.getPropertyName(artefact)
        def ctx = grailsApplication.getMainContext()
        Worker bean = ctx.getBean(name)
        log.info "registering ${name} as a Worker"
        if(bean) {
          services[bean.getJobClass()] = bean
        }
      }
    }
    log.info "There are ${services.size()} registered workers"
  }

  /**
   * dispatches the job to any worker services registered to handle a job of this type
   * you may
   * @param job
   * @return job with markup (save to persist)
   */
  def dispatch(job) {
    try {
      //services.each { Worker worker ->  worker.dispatch(job) }
      Worker service = services.getAt(job.getClass())
      if(service == null) throw new Exception("There is no service registered to work on ${job.getClass()}")
      println "This is a job for ${service.getClass().getSimpleName().replaceAll("\\\$.*\$","")}"
      service.doWork(job)
    } catch (Throwable t) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw, true);
      t.printStackTrace(pw);
      pw.flush();
      sw.flush();
      job.errorMessage = """${t.getClass()}: '${t.getMessage()}'\n${sw.toString()}\n"""
    } finally {
      job.completed = true
    }
    return job
  }
}
