# YetAnotherMinesweeperClone
Giving Scala.js a try...

Following the tutorial (http://www.scala-js.org/doc/tutorial.html) I've got the Scala.js compiler up and running.
I still have to do some fiddling with the project setup and Intellij IDEA support. Though more or less it is working!

Ok... first issue I bumped into (because of my recklessness). Scala objects correspond to JS functions. You have to
invoke the JS function generated for it, that will return a JavaScript object that contains the methods of your Scala object.
Or at least stubs to it. But it works that way... so the solution is:

 com.norbertsram.yamc.YetAnotherMinesweeperCloneApp().main().

 Note the parantheses after 'YetAnotherMinesweeperCloneApp'! I didn't in the tutorial.

 Now that I see that it indeed does work, time to set up some kind of LiveReload support.