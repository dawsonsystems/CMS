package cms

import org.weceem.content.WcmSpace
import org.apache.shiro.SecurityUtils
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext

class DomainFilters implements ApplicationContextAware {

  static String WCM_ADMIN = "/wcm/admin/**"

  ApplicationContext applicationContext

  def filters = {
    allExceptAdmin(uri: WCM_ADMIN, invert:true) {
      before = {
        def requestedName = request.getHeader('Host')
        if (!requestedName)  {
          requestedName = request.getHeader('X-Real-Host')
        }
        request.space = WcmSpace.findByAliasURI(requestedName,[cache:true])
      }
    }

    listSpaces(uri:WCM_ADMIN) {
      before = {
        if (SecurityUtils.subject) {
          request.spaces = WcmSpace.listOrderByName().findAll {
            def auth = SecurityUtils.subject.isPermitted("space:"+it.aliasURI)
            return auth
          }
        }
      }
    }

    ensureSelectedSpaceIsAuthorised(uri:WCM_ADMIN) {
      before = {
        println "I AM ENTERING THE ADMIN SPACE THING"
        if (params.space == null || (params.space instanceof String && params.space.length() == 0)) {
          //the user is logging in, and has selected no space.
          //we need to find their preferred, or indeed any space they can access and select that for them.
          def authSpace = getFirstAuthorisedSpace()
          if (authSpace) {
            println "User logged in with no preselected space, chose ${authSpace} as their initial space, which has correct authorisation."
            request.space = authSpace
            return
          }
        }

        if (params.space && params.space instanceof String) {
          def space = WcmSpace.findByName(params.space)
          println "Checking space.... $space"
          if (space && SecurityUtils.subject.isPermitted("space:${space.aliasURI}")) {
            println "User is permitted to view space ${space.name}"
            request.space = space
            return
          }
          if (space) {
            flash.error="You are not authorised to view the space '${space.name}' - code:0001"
            request.space = getFirstAuthorisedSpace()
            params.space = request.space.name
            return
          }
        }
        if (params.space?.id) {
          def space = WcmSpace.findById(params.space.id)
          println "Checking security for ${space.name}"
          if (space && SecurityUtils.subject.isPermitted("space:${space.aliasURI}")) {
            request.space = space
            return
          }
          if (space) {
            flash.error="You are not authorised to view the space '${space.name}' - code:0002"
            request.space = getFirstAuthorisedSpace()
            params.space = request.space.name
            return
          }
        }

        if (session.currentAdminSpace) {
          def space = WcmSpace.findById(session.currentAdminSpace)
          if (space && SecurityUtils.subject.isPermitted("space:${space.aliasURI}")) {
            request.space = space
            return
          }
          if (space) {
            flash.error="You are not authorised to view the space '${space.name}' - code:0003"
            request.space = getFirstAuthorisedSpace()
            return
          }
        }

        //check the default space
        def space = applicationContext.getBean("wcmContentRepositoryService").findDefaultSpace()
        if (space && SecurityUtils.subject.isPermitted("space:${space.aliasURI}")) {
          request.space = space
          return
        }
        if (space) {
          flash.error="You are not authorised to view the space '${space.name}' - code:0004"
          request.space = getFirstAuthorisedSpace()
          return
        }

        //if we've not got a selected space, choose on from the authorized list, if none exist, redirect to the current profile with a flash error.
        space = getFirstAuthorisedSpace()

        if (space) {
          request.space = space
          return
        }

        flash.error="You are not authorised to view any spaces"
        redirect(controller:"profile", action:"showCurrent")
        return false
      }
    }
  }

  def getFirstAuthorisedSpace() {
    def authSpace = WcmSpace.list().find {
      SecurityUtils.subject.isPermitted("space:${it.aliasURI}")
    }
    return authSpace
  }
}
