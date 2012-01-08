import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.springframework.context.ApplicationContext
import cms.ShiroRole
import cms.ShiroUser

class BootStrap {

    def init = { servletContext ->
      ApplicationContext context = servletContext.getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT)
      if (!ShiroRole.findByName("admin")) {
        log.info("Generating Admin Role")
        ShiroRole userRole = new ShiroRole(name:"admin")
        userRole.addToPermissions("*:*")
        userRole.save()

        log.info("Generating Admin User")
        def user = new ShiroUser(email:"david.dawson@dawsonsystems.com", firstName: "David", lastName: "Dawson", username: "david", passwordHash: new Sha256Hash("examplepass").toHex())
        user.addToPermissions("*:*")
        user.addToRoles(userRole)
        user.save(flush:true)
      }

      context.wcmSecurityService.securityDelegate = [
        getUserName : { ->
          return SecurityUtils.subject.principal
        },
        getUserEmail : { ->
          return ShiroUser.findByUsername(SecurityUtils.subject.principal)?.email
        },
        getUserRoles : { ->
          return ["ROLE_ADMIN"]
        },
        getUserPrincipal : { ->
          return SecurityUtils.subject.principal ?: [:]
        }
      ]
    }
    def destroy = {
    }
}
