name := "wcs-info"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings


//lazy val parsers = ProjectRef(file("../wcs-config-parsers"), "wcs-config-parsers")

lazy val parsers = RootProject(file("../wcs-config-parsers"))

val main = Project(id = "wcs-info", base = file(".")).dependsOn(parsers) 