import groovymag.services.JobDispatchService

class BootStrap {
     JobDispatchService jobDispatchService
     def init = { servletContext ->
       jobDispatchService.init() // must come up after bean auto-wiring is complete
     }
     def destroy = {
     }
} 