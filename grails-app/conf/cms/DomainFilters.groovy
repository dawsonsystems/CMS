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

        if (params.space) {
          def space = WcmSpace.findByName(params.space)
          if (space && SecurityUtils.subject.isPermitted("space:${space.aliasURI}")) {
            request.space = space
            return
          }
          if (space) {
            flash.error="You are not authorised to view the space ${space.aliasURI}"
          }
        }

        if (session.currentAdminSpace) {
          def space = WcmSpace.findById(session.currentAdminSpace)
          if (space && SecurityUtils.subject.isPermitted("space:${space.aliasURI}")) {
            request.space = space
            return
          }
          if (space) {
            flash.error="You are not authorised to view the space ${space.aliasURI}"
          }
        }

        //check the default space
        def space = applicationContext.getBean("wcmContentRepositoryService").findDefaultSpace()
        if (space && SecurityUtils.subject.isPermitted("space:${space.aliasURI}")) {
          request.space = space
          return
        }
        if (space) {
          flash.error="You are not authorised to view the space ${space.aliasURI}"
        }

        //if we've not got a selected space, choose on from the authorized list, if none exist, redirect to the current profile with a flash error.
        def authorisedSpaces = WcmSpace.listOrderByName().findAll {
          SecurityUtils.subject.isPermitted("space:"+it.aliasURI)
        }

        if (authorisedSpaces) {
          request.space = authorisedSpaces[0]
          return
        }

        flash.error="You are not authorised to view any spaces"
        redirect(controller:"profile", action:"showCurrent")
        return false
      }
    }
  }
}
