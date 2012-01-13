grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"

if(System.getProperty('build')){
	println "Running with installed plugins..."
} else {
	println "Running with inline plugins happily..."
  grails.plugin.location.gwt="/home/david/Development/Source/opensource/grails-gwt"
	grails.plugin.location.weceem="/home/david/Development/Source/opensource/weceem-plugin"
}

gwt {
  version="2.4.0"
  gin.version="1.5.0"
  dependencies=['com.dawsonsystems:gxt:2.2.5-gwt22','com.dawsonsystems:gxt-multi-upload:0.1']
  //output.path="${basedir}/web-app"
}

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenCentral()
        mavenRepo "https://github.com/dawsonsystems/mavenrepo/raw/master/snapshots"
    }
    dependencies {
      compile 'mysql:mysql-connector-java:5.1.18'
      compile 'com.google.inject:guice:3.0'
      compile 'org.apache.commons:commons-lang3:3.1'
        // runtime 'mysql:mysql-connector-java:5.1.13'
//      compile ('org.openid4java:openid4java:0.9.6') {
//        excludes 'xml-apis', "guice"
//      }
    }
}
