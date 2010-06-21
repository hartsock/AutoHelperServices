package groovymag.jobs

class ManController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [manInstanceList: Man.list(params), manInstanceTotal: Man.count()]
    }

    def create = {
        def manInstance = new Man()
        manInstance.properties = params
        return [manInstance: manInstance]
    }

    def save = {
        def manInstance = new Man(params)
        if (manInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'man.label', default: 'Man'), manInstance.id])}"
            redirect(action: "show", id: manInstance.id)
        }
        else {
            render(view: "create", model: [manInstance: manInstance])
        }
    }

    def show = {
        def manInstance = Man.get(params.id)
        if (!manInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'man.label', default: 'Man'), params.id])}"
            redirect(action: "list")
        }
        else {
            [manInstance: manInstance]
        }
    }

    def edit = {
        def manInstance = Man.get(params.id)
        if (!manInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'man.label', default: 'Man'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [manInstance: manInstance]
        }
    }

    def update = {
        def manInstance = Man.get(params.id)
        if (manInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (manInstance.version > version) {
                    
                    manInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'man.label', default: 'Man')] as Object[], "Another user has updated this Man while you were editing")
                    render(view: "edit", model: [manInstance: manInstance])
                    return
                }
            }
            manInstance.properties = params
            if (!manInstance.hasErrors() && manInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'man.label', default: 'Man'), manInstance.id])}"
                redirect(action: "show", id: manInstance.id)
            }
            else {
                render(view: "edit", model: [manInstance: manInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'man.label', default: 'Man'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def manInstance = Man.get(params.id)
        if (manInstance) {
            try {
                manInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'man.label', default: 'Man'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'man.label', default: 'Man'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'man.label', default: 'Man'), params.id])}"
            redirect(action: "list")
        }
    }
}
