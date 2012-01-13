package cms

class Role {
  String name

  static hasMany = [permissions:String]

  static constraints = {
  }
}
