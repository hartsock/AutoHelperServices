package groovymag.jobs

class DuckController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [duckInstanceList: Duck.list(params), duckInstanceTotal: Duck.count()]
    }

    def create = {
        def duckInstance = new Duck()
        duckInstance.properties = params
        return [duckInstance: duckInstance]
    }

    def save = {
        def duckInstance = new Duck(params)
        if (duckInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'duck.label', default: 'Duck'), duckInstance.id])}"
            redirect(action: "show", id: duckInstance.id)
        }
        else {
            render(view: "create", model: [duckInstance: duckInstance])
        }
    }

    def show = {
        def duckInstance = Duck.get(params.id)
        if (!duckInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'duck.label', default: 'Duck'), params.id])}"
            redirect(action: "list")
        }
        else {
            [duckInstance: duckInstance]
        }
    }

    def edit = {
        def duckInstance = Duck.get(params.id)
        if (!duckInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'duck.label', default: 'Duck'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [duckInstance: duckInstance]
        }
    }

    def update = {
        def duckInstance = Duck.get(params.id)
        if (duckInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (duckInstance.version > version) {
                    
                    duckInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'duck.label', default: 'Duck')] as Object[], "Another user has updated this Duck while you were editing")
                    render(view: "edit", model: [duckInstance: duckInstance])
                    return
                }
            }
            duckInstance.properties = params
            if (!duckInstance.hasErrors() && duckInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'duck.label', default: 'Duck'), duckInstance.id])}"
                redirect(action: "show", id: duckInstance.id)
            }
            else {
                render(view: "edit", model: [duckInstance: duckInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'duck.label', default: 'Duck'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def duckInstance = Duck.get(params.id)
        if (duckInstance) {
            try {
                duckInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'duck.label', default: 'Duck'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'duck.label', default: 'Duck'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'duck.label', default: 'Duck'), params.id])}"
            redirect(action: "list")
        }
    }
}
