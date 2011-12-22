import org.apache.shiro.crypto.hash.Sha256Hash

class BootStrap {

    def init = { servletContext ->

      if (!ShiroRole.findByName("admin")) {
        log.info("Generating Admin Role")
        ShiroRole userRole = new ShiroRole(name:"admin")
        userRole.addToPermissions("*:*")
        userRole.save()

        log.info("Generating Admin User")
        def user = new ShiroUser(username: "david", passwordHash: new Sha256Hash("examplepass").toHex())
        user.addToPermissions("*:*")
        user.addToRoles(userRole)
        user.save(flush:true)
      }
    }
    def destroy = {
    }
}
