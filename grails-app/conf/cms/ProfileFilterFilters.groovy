package cms

import org.apache.shiro.SecurityUtils
import cms.Logon

class ProfileFilterFilters {

  def filters = {
    all(controller: '*', action: '*') {
      before = {
        if (SecurityUtils.subject.authenticated) {
          request.profile = Logon.findByIdentifier(SecurityUtils.subject.principal).profile
        }
      }
      after = {

      }
      afterView = {

      }
    }
  }

}
