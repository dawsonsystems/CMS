package cms

class LocalLogonController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index = {
    redirect(action: "list", params: params)
  }

  def list = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [localLogonInstanceList: LocalLogon.list(params), localLogonInstanceTotal: LocalLogon.count()]
  }

  def create = {
    def localLogonInstance = new LocalLogon()
    localLogonInstance.properties = params
    return [localLogonInstance: localLogonInstance]
  }

  def save = {
    def localLogonInstance = new LocalLogon(params)
    if (localLogonInstance.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'localLogon.label', default: 'LocalLogon'), localLogonInstance.id])}"
      redirect(action: "show", id: localLogonInstance.id)
    }
    else {
      render(view: "create", model: [localLogonInstance: localLogonInstance])
    }
  }

  def show = {
    def localLogonInstance = LocalLogon.get(params.id)
    if (!localLogonInstance) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'localLogon.label', default: 'LocalLogon'), params.id])}"
      redirect(action: "list")
    }
    else {
      [localLogonInstance: localLogonInstance]
    }
  }

  def edit = {
    def localLogonInstance = LocalLogon.get(params.id)
    if (!localLogonInstance) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'localLogon.label', default: 'LocalLogon'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [localLogonInstance: localLogonInstance]
    }
  }

  def update = {
    def localLogonInstance = LocalLogon.get(params.id)
    if (localLogonInstance) {
      if (params.version) {
        def version = params.version.toLong()
        if (localLogonInstance.version > version) {

          localLogonInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'localLogon.label', default: 'LocalLogon')] as Object[], "Another user has updated this LocalLogon while you were editing")
          render(view: "edit", model: [localLogonInstance: localLogonInstance])
          return
        }
      }
      localLogonInstance.properties = params
      if (!localLogonInstance.hasErrors() && localLogonInstance.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'localLogon.label', default: 'LocalLogon'), localLogonInstance.id])}"
        redirect(action: "show", id: localLogonInstance.id)
      }
      else {
        render(view: "edit", model: [localLogonInstance: localLogonInstance])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'localLogon.label', default: 'LocalLogon'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    def localLogonInstance = LocalLogon.get(params.id)
    if (localLogonInstance) {
      try {
        localLogonInstance.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'localLogon.label', default: 'LocalLogon'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'localLogon.label', default: 'LocalLogon'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'localLogon.label', default: 'LocalLogon'), params.id])}"
      redirect(action: "list")
    }
  }
}
