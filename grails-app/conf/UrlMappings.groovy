class UrlMappings {

	static mappings = {
		"/"(controller:"home", action:"index")
    "/auth/login"(controller:"auth", action:"login")
    "/auth/lostPassword"(controller:"auth", action:"lostPassword")
	}
}
