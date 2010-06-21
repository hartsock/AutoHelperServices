package groovymag.jobs

class SuperManController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [superManInstanceList: SuperMan.list(params), superManInstanceTotal: SuperMan.count()]
    }

    def create = {
        def superManInstance = new SuperMan()
        superManInstance.properties = params
        return [superManInstance: superManInstance]
    }

    def save = {
        def superManInstance = new SuperMan(params)
        if (superManInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'superMan.label', default: 'SuperMan'), superManInstance.id])}"
            redirect(action: "show", id: superManInstance.id)
        }
        else {
            render(view: "create", model: [superManInstance: superManInstance])
        }
    }

    def show = {
        def superManInstance = SuperMan.get(params.id)
        if (!superManInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'superMan.label', default: 'SuperMan'), params.id])}"
            redirect(action: "list")
        }
        else {
            [superManInstance: superManInstance]
        }
    }

    def edit = {
        def superManInstance = SuperMan.get(params.id)
        if (!superManInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'superMan.label', default: 'SuperMan'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [superManInstance: superManInstance]
        }
    }

    def update = {
        def superManInstance = SuperMan.get(params.id)
        if (superManInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (superManInstance.version > version) {
                    
                    superManInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'superMan.label', default: 'SuperMan')] as Object[], "Another user has updated this SuperMan while you were editing")
                    render(view: "edit", model: [superManInstance: superManInstance])
                    return
                }
            }
            superManInstance.properties = params
            if (!superManInstance.hasErrors() && superManInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'superMan.label', default: 'SuperMan'), superManInstance.id])}"
                redirect(action: "show", id: superManInstance.id)
            }
            else {
                render(view: "edit", model: [superManInstance: superManInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'superMan.label', default: 'SuperMan'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def superManInstance = SuperMan.get(params.id)
        if (superManInstance) {
            try {
                superManInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'superMan.label', default: 'SuperMan'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'superMan.label', default: 'SuperMan'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'superMan.label', default: 'SuperMan'), params.id])}"
            redirect(action: "list")
        }
    }
}
