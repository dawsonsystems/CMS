package cms

import org.weceem.content.WcmSpace

class DomainFilters {

  static String ALL_EXCEPT_ADMIN = "/wcmadmin/**"

  def filters = {
    all(uri: ALL_EXCEPT_ADMIN, invert:true) {
      before = {
        def requestedName = request.getHeader('Host')
        println "Requested Host : $requestedName"
        if (!requestedName)  {
          requestedName = request.getHeader('X-Real-Host')
          println "Requested X-Real-Host : $requestedName"
        }
        request.space = WcmSpace.findByAliasURI(requestedName,[cache:true])
      }
    }
  }
}
