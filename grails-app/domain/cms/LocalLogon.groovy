package cms

class LocalLogon extends Logon {
  String passwordHash

  static constraints = {
    passwordHash blank:false
  }
}
