import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.SimpleAccount
import cms.LocalLogon
import cms.Profile
import cms.Logon

class ShiroDbRealm {
  static authTokenClass = org.apache.shiro.authc.UsernamePasswordToken

  def credentialMatcher
  def shiroPermissionResolver

  def authenticate(authToken) {
    println "Attempting to authenticate ${authToken.username} in DB realm..."
    log.warn "Attempting to authenticate ${authToken.username} in DB realm..."
    def username = authToken.username

    // Null username is invalid
    if (username == null) {
      throw new AccountException("Null usernames are not allowed by this realm.")
    }

    // Get the user with the given username. If the user is not
    // found, then they don't have an account and we throw an
    // exception.
    def user = LocalLogon.findByIdentifier(username)
    if (!user) {
      println "No account of that name..."
      throw new UnknownAccountException("No account found for user [${username}]")
    }

    println "Found user '${user.identifier}' in DB"

    // Now check the user's password against the hashed value stored
    // in the database.
    def account = new SimpleAccount(username, user.passwordHash, "ShiroDbRealm")
    if (!credentialMatcher.doCredentialsMatch(authToken, account)) {
      println "Invalid password (DB realm)"
      throw new IncorrectCredentialsException("Invalid password for user '${username}'")
    }

    return account
  }

  def hasRole(principal, roleName) {
    return LocalLogon.findByIdentifier(principal).profile.roles.find { it.name == roleName} != null
  }

  def hasAllRoles(principal, roles) {
    throw new IllegalStateException("Not Implemented")
    //return LocalLogon.findByUsername(principal).profile.roles.findAll { it.name == roleName}.size() == roles.size()
  }

  def isPermitted(principal, requiredPermission) {
    Profile user = Logon.findByIdentifier(principal)?.profile
    def permissions = user.permissions

    // Try each of the permissions found and see whether any of
    // them confer the required permission.
    def retval = permissions?.find { permString ->
        // Create a real permission instance from the database
        // permission.
        def perm = shiroPermissionResolver.resolvePermission(permString)

        // Now check whether this permission implies the required
        // one.
        if (perm.implies(requiredPermission)) {
            // User has the permission!
            return true
        }
        else {
            return false
        }
    }

    if (retval != null) {
        // Found a matching permission!
        return true
    }

    // If not, does he gain it through a role?
    //
    // Get the permissions from the roles that the user does have
//        def results = Profile.executeQuery("select distinct p from Profile as user join user.roles as role join role.permissions as p where user.username = '$principal'")

    permissions = user.roles.collect { it.permissions }.flatten() as Set

    // There may be some duplicate entries in the results, but
    // at this stage it is not worth trying to remove them. Now,
    // create a real permission from each result and check it
    // against the required one.
    retval = permissions.find { permString ->
        // Create a real permission instance from the database
        // permission.
        def perm = shiroPermissionResolver.resolvePermission(permString)

        // Now check whether this permission implies the required
        // one.
        if (perm.implies(requiredPermission)) {
            // User has the permission!
            return true
        }
        else {
            return false
        }
    }

    if (retval != null) {
        // Found a matching permission!
        return true
    }
    else {
        return false
    }
  }
}
