import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)

workbenchSettings

name := "html2scalajs-react"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.2",
  "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
  "com.lihaoyi" %%% "scalatags" % "0.5.4"
)

jsDependencies +=
    "org.webjars" % "jquery" % "2.1.3" / "2.1.3/jquery.js"


bootSnippet := "com.mdelcid.H2RMain().main();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

