#YetAnotherMinesweeperClone A.K.A Giving Scala.js a try...
Here is where I document my adventures and thoughts as I play with Scala.js. The goal is to create a working Minesweeper clone.
If various factors - ranging from life to me not liking what Scala.js gives me - do not intervene I will also provide a canvas based renderer.

### Setting up the development environment

You should have no trouble getting started with Scala.js if you follow the [tutorial](http://www.scala-js.org/doc/tutorial.html).
I've got the Scala.js compiler up and running based on that. I'm using IntelliJ IDEA for Scala editing, since Scala.js is just Scala. 
I still have to do some fiddling with the project setup, though more or less it is working! I have to admit, I'm surprised how everything works out of the box!

Time to set up some kind of LiveReload support! There is already a file watcher to recompile your application. You can access that
using the **~fastOptJs** SBT target.
The live reload solution provided by [workbench](https://github.com/lihaoyi/workbench) works as well. 
The documentation not perfect, but the [example project](https://github.com/lihaoyi/workbench-example-app) contains a working solution.
TODO: write down what I did to get it working

Source mapping also works fine. **TODO: picture or it didn't happen!**

### Creating the ultimate minesweeper application

### Stuff I bumped into

#### Scala objects are mapped to JavaScript objects, BUT you acquire them through a JavaScript function.

This was the first issue I bumped into (because of my recklessness). I tried to treat Scala objects as JavaScript object.
Meaning I tried to do something like:

```
com.norbertsram.yamc.YetAnotherMinesweeperCloneApp.main().
```

Where _YetAnotherMinesweeperCloneApp_ is a Scala object. This is where I learned that Scala objects are not **directly** mapped to JavaScript objects. You have to
invoke the JS function generated for it. It will return a JavaScript object that contains the methods of your Scala object. So I wasn't to far off. 
The correct solution looks like this:

```
com.norbertsram.yamc.YetAnotherMinesweeperCloneApp().main().
```

 **Note the parentheses after _YetAnotherMinesweeperCloneApp_!** I didn't! I if you read the tutorial
 carefully - unlike me - and you are not colorblind then you will see that it is NOTED with the color red.
 At the end of the day Scala objects are JavaScript objects returned by a JavaScript function. In the tutorial you'll find the reason for that.
 I'll save some time and quote the reason here. You can thank me later by spreading the news about the most awesome Minesweeper ever!
 
 > A Scala object is a function in JavaScript, since potential object initialization code needs to be run upon the first access to the object.

 