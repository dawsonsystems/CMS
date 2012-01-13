package cms

class Logon {
  String identifier
  Profile profile
  static belongsTo = [Profile]

  static constraints = {
    identifier(blank:false)
  }
}
