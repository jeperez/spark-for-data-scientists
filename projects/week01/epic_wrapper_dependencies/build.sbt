lazy val root = (project in file(".")).
  settings(
    name := "Example",
    version := "1.0",
    scalaVersion := "2.10.4",
    libraryDependencies += "org.scalanlp" % "epic_2.10" % "0.3.1",
    libraryDependencies += "org.scalanlp" %% "english"  % "2015.2.19"
  )
