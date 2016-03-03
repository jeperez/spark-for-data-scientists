lazy val root = (project in file(".")).
  settings(
    name := "Example",
    version := "1.0",
    scalaVersion := "2.10.4",
    libraryDependencies += "org.scalanlp" % "epic_2.10" % "0.3.1",
    libraryDependencies += "org.scalanlp" %% "english"  % "2015.2.19",
      mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
        {
          case PathList("org", "w3c", "dom", _) => MergeStrategy.first
          case PathList("javax", "xml", "stream", _ *) => MergeStrategy.first
          case PathList("scala", "xml", _ *) => MergeStrategy.first
          case PathList("org", "cyberneko", "html", _ *) => MergeStrategy.first
          case x => old(x)
         }
      }
  )
