package cms

import org.weceem.content.WcmSpace

class DomainFilters {

  static String ALL_EXCEPT_ADMIN = "/wcmadmin/**"

  def filters = {
    all(uri: ALL_EXCEPT_ADMIN, invert:true) {
      before = {
        def requestedName = request.getHeader('Host')
        if (!requestedName)  {
          requestedName = request.getHeader('X-Real-Host')
        }
        request.space = WcmSpace.findByAliasURI(requestedName,[cache:true])
      }
    }
  }
}
