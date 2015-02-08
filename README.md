#YetAnotherMinesweeperClone A.K.A Giving Scala.js a try...
Here is where I document my adventures and thoughts as I play with Scala.js. The goal is to create a working Minesweeper clone, from here on refered to as YAMC.
If various factors - ranging from life to me not liking what Scala.js gives me - do not intervene I will also provide a canvas based renderer.

### Setting up the development environment

You should have no trouble getting started with Scala.js if you follow the [tutorial](http://www.scala-js.org/doc/tutorial.html).
I've got the Scala.js compiler up and running based on that. I'm using IntelliJ IDEA for Scala editing, since Scala.js is just Scala. 
I still have to do some fiddling with the project setup, though more or less it is working! I have to admit, I'm surprised how everything works out of the box!

Time to set up some kind of LiveReload support! There is already a file watcher to recompile your application. You can access that
using the _~fastOptJs_ SBT target.
The live reload solution provided by [workbench](https://github.com/lihaoyi/workbench) works as well. 
The documentation not perfect, but the [example project](https://github.com/lihaoyi/workbench-example-app) contains a working solution.
It doesn't take much work to add it to your own project. Just follow the guide found on the page of [workbench](https://github.com/lihaoyi/workbench).
The only thing that needs to be changed is how you trigger Live Reloading. **Instead of packageJS you need to use fastOptJs**.

```
updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
```
  
For the complete solution, just check the commit where I enabled workbench [bd50e20160b5cb53aa9d5851061e2b1f0737da00](https://github.com/snorbi07/YetAnotherMinesweeperClone/commit/bd50e20160b5cb53aa9d5851061e2b1f0737da00).

Usage is quite simple. Starting _sbt_ in the project will automatically create a webserver (by default it listens on _localhost:12345_) that hosts your project.
Next step is to start the file watcher based compilation in the same console. So just type _~fastOptJs_. From here on you are all set. Fire up a browser, and 
open your startup page through workbench's webserver. In case of YAMC, this is _http://127.0.0.1:12345/src/main/html/yamc.html_

Source mapping also works fine. **TODO: picture or it didn't happen!**

### Creating the ultimate minesweeper application

#### Dependency management
Besides having a WebJars for JavaScript libraries, Scala.js also provides support for publishing your application at its 3rd party dependencies.
Adding a single line to your SBT build file (_build.sbt_) provides you with a bundle that contains all of your JavaScript dependencies.

```
skip in packageJSDependencies := false
```

No more CDN, ext folder or whatever setup. Best thing is that Scala.js libraries that you get using WebJars support this.
The final JavaScript bundle contains the transient JavaScript dependencies as well. **You can just build, deploy and stop worrying.**

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


#### The Live Reload functionality provided by workbench and ~fastOptJs works only with Scala sources
In case you start editing your HTML file, it won't reload your application. You are stuck with the manual refresh.
There might be an easy solution for this. I wasn't keen on looking since it doesn't hinder me much.

#### Either you Live Reload or have source mappings
With the Live Reload setup of workbench you don't have source mapping support. You do not have access to the Scala sources, or
at least Chrome's Developers Tools does not find it. I've tried adding it with the _Add Source Map..._ menu item, but it did not do anything.
I'll have to check out the workbench examples whether it works there. I have to admit, I'm not happy about this.




 
 