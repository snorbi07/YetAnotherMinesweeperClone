enablePlugins(ScalaJSPlugin)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"
libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.8.0"
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test"

jsDependencies += RuntimeDOM
skip in packageJSDependencies := false

testFrameworks += new TestFramework("utest.runner.Framework")
name := "YetAnotherMinesweeperClone"

version := "1.0"

scalaVersion := "2.11.5"

workbenchSettings

bootSnippet := "com.norbertsram.yamc.YetAnotherMinesweeperCloneApp().main()"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)