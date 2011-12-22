class UrlMappings {

	static mappings = {
		"/"(controller:"home", action:"index")
    "/auth/login"(controller:"auth", action:"login")
    "/auth/lostPassword"(controller:"auth", action:"lostPassword")
    "/auth/signIn"(controller:"auth", action:"signIn")
    "/auth/signOut"(controller:"auth", action:"signOut")
    "/wcm/admin/users/current"(controller:"shiroUser", action:"showCurrent")
	}
}
