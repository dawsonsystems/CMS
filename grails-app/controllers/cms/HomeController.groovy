package cms

class HomeController {

  def index = {
    println "Using Home"
    redirect(uri:"/c")
  }
}
