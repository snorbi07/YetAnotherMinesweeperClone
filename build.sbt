enablePlugins(ScalaJSPlugin)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"

name := "YetAnotherMinesweeperClone"

version := "1.0"

scalaVersion := "2.11.5"

workbenchSettings

bootSnippet := "com.norbertsram.yamc.YetAnotherMinesweeperCloneApp().main()"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
