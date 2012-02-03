package cms

import org.weceem.content.WcmSpace

class Profile {

  String displayName
  String picture
  String email

  //TODO, accessing the space permission like this may become a performance issue.
  static hasMany = [logins:Logon, realPermissions:String, roles:Role, permittedSpaces:WcmSpace]

  static transients = ['permissions']

  def getPermissions() {
    return realPermissions + permittedSpaces.collect { "space:" + it.aliasURI  }
  }

  static constraints = {
    displayName(blank:false)
    picture(nullable:true)
    email(nullable:true)
  }
}
