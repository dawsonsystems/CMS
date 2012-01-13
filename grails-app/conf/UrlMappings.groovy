class UrlMappings {

	static mappings = {
		"/"(controller:"home", action:"index")
    "/auth/login"(controller:"auth", action:"login")
    "/auth/lostPassword"(controller:"auth", action:"lostPassword")
    "/auth/signIn"(controller:"auth", action:"signIn")
    "/auth/signOut"(controller:"auth", action:"signOut")
    "/wcm/admin/users/current"(controller:"shiroUser", action:"showCurrent")

    //Shop specific.
    "/col/$id/$name"(controller:"home", action:"collection")
    "/p/$id/$name"(controller:"home", action:"product")
    "/basket"(controller:"home", action:"basket")
    "/basket/add"(controller:"basket", action:"add")
    "/basket/remove"(controller:"basket", action:"remove")
    "/basket/update"(controller:"basket", action:"update")
    "/basket/clear"(controller:"basket", action:"clear")
    "/basket/checkout"(controller:"basket", action:"checkout")
    "/stock"(controller:"stock", action:"index")
    "/stock/upload"(controller:"stock", action:"upload")

	}
}
