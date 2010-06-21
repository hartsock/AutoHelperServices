package groovymag.jobs

class RabbitController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [rabbitInstanceList: Rabbit.list(params), rabbitInstanceTotal: Rabbit.count()]
    }

    def create = {
        def rabbitInstance = new Rabbit()
        rabbitInstance.properties = params
        return [rabbitInstance: rabbitInstance]
    }

    def save = {
        def rabbitInstance = new Rabbit(params)
        if (rabbitInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'rabbit.label', default: 'Rabbit'), rabbitInstance.id])}"
            redirect(action: "show", id: rabbitInstance.id)
        }
        else {
            render(view: "create", model: [rabbitInstance: rabbitInstance])
        }
    }

    def show = {
        def rabbitInstance = Rabbit.get(params.id)
        if (!rabbitInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rabbit.label', default: 'Rabbit'), params.id])}"
            redirect(action: "list")
        }
        else {
            [rabbitInstance: rabbitInstance]
        }
    }

    def edit = {
        def rabbitInstance = Rabbit.get(params.id)
        if (!rabbitInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rabbit.label', default: 'Rabbit'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [rabbitInstance: rabbitInstance]
        }
    }

    def update = {
        def rabbitInstance = Rabbit.get(params.id)
        if (rabbitInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (rabbitInstance.version > version) {
                    
                    rabbitInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'rabbit.label', default: 'Rabbit')] as Object[], "Another user has updated this Rabbit while you were editing")
                    render(view: "edit", model: [rabbitInstance: rabbitInstance])
                    return
                }
            }
            rabbitInstance.properties = params
            if (!rabbitInstance.hasErrors() && rabbitInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'rabbit.label', default: 'Rabbit'), rabbitInstance.id])}"
                redirect(action: "show", id: rabbitInstance.id)
            }
            else {
                render(view: "edit", model: [rabbitInstance: rabbitInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rabbit.label', default: 'Rabbit'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def rabbitInstance = Rabbit.get(params.id)
        if (rabbitInstance) {
            try {
                rabbitInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'rabbit.label', default: 'Rabbit'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'rabbit.label', default: 'Rabbit'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rabbit.label', default: 'Rabbit'), params.id])}"
            redirect(action: "list")
        }
    }
}
