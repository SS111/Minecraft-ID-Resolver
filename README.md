#Minecraft ID Resolver - A modpack creators best friend!
Welcome! I have created a tool which I believe both normal users and modpack creators will be able to utilize. It is written in Java and therefore will work on every OS and is designed to find and fix Minecraft ID conflicts.

##Usage

1. Select the configuration directory
2. Search for conflicts
3. Manually resolve conflicts by using the list of conflicts the program provides

Please note that the unknown IDs tab is an attempt to support unsupported configuration files (aka any non-forge configuration file) and is **most likely always** going to be a false positive. Because of this, this tool *will not* attempt to resolve unknown ID conflicts.

Or if you want to resolve ID conflicts automatically,

1. Select the configuration directory
2. Search for conflicts
3. Provide an [NEI](http://www.minecraftforum.net/topic/909223-147152-smp-chickenbones-mods/) ID dump     
    1. **For Minecraft 1.5.2 and below:** 
        2. Start Minecraft with NEI installed
        3. Open any world
        4. Open your inventory
        5. Click "Options"
        6. Click "Block/Item ID Settings"
        7. Make sure that item IDs are dumped! It **will not** dump item IDs by default!
        8. Click "Dump ID Map Now". This will dump the ID map to your ``.minecraft`` folder. Now just browse to the file when asked to provide a NEI ID dump.   
    2. **For Minecraft 1.6.2 and above:**
        1. Start Minecraft with NEI installed
        2. Open any world
        3. Open your inventory
        4. Click "Options"
        5. Click "Tools"
        6. Click "Data Dumps"
        7. Make sure that all the block/item IDs or the free block/item IDs are dumped! **Do not** dump the already used IDs!
        8. Click "Dump". This will dump the ID map to your ``.minecraft/dumps`` folder. Now just browse to the file when asked to provide a NEI ID dump.
4. Allow the program to resolve conflicts automatically

If for whatever reason your configuration is messed up afterwards, there will be a backup in your .minecraft directory named config_bak.
- - -
As of version 1.0.5, there is now command line support. The basic usage goes as follows:

```
Usage: java -jar Minecraft.ID.Resolver.v1.0.5.jar
                (-c|--config) <config dir> (-n|--dump) <nei id dump> [(-s|--show) <show conflicts>]

  (-c|--config) <config dir>
        The path to the Minecraft configuration directory.

  (-n|--dump) <nei id dump>
        The path to the NEI ID dump file.

  [(-s|--show) <show conflicts>]
        Requests that conflicts be displayed before being resolved. (default: false)
```

Also, do note that the -s argument is not necessary unless you want to set it to true, in which case the conflicts will be displayed and you will be asked if you want to resolve them, rather than them being resolved without user input.

A basic example also goes as follows:

```
java -jar Minecraft.ID.Resolver.v.1.0.5.jar -c C:\Users\Bob\AppData\Roaming\.minecraft\config\ -n C:\Users\Bob\AppData\Roaming\.minecraft\dump.txt
```

##Building from source

If you just want a stable build, grab the latest version from the [releases page](https://github.com/SS111/Minecraft-ID-Resolver/releases).

However, if you want the bleeding edge build first clone the repository. Then you are going to need to add [MiG Layout](http://www.miglayout.com/), [Apache Commons Collections](http://commons.apache.org/proper/commons-collections/), [Appache Commons IO](http://commons.apache.org/proper/commons-io/), and [JSAP](http://www.martiansoftware.com/jsap/) to the build path. Finally, make sure that the classpath is set to WindowMain and then compile.

##Contributing

Contributing is a great way to help me and the community out. Everything helps!

##Donating

Donating is one way to express your thanks for the work I do in my free time. You can donate any amount [here](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=DBT788PS4EN8J).

##Pull requests

Contirbuting to my code is another wonderful way to help me out. I will accept just about any pull requests.

However, I do ask that:

1. Don't make any readme changes unless its of *vast importance*
2. Don't just change a variable or function name and call it a pull request. Please only commit changes that add new content or changes something that is *very important*
3. Follow my coding style

###My coding style

When making a pull request, I ask that you follow a few simple format rules that I use so that even in the future the code still flows nicely.

Rules:

+ Keep the first brace on the opening line

e.g.

```java
public static void foo() {

  return bar;

}
```

not

```java
public static void foo()
{
    return bar;
}
```

+ Separate actions/variable manipulations that are different
+ However, breaks, continues, etc. *can* stay directly under any action.

e.g.

```java
public static String doStuffAndGetFoo() {

bar.setValue("foo");
bar.setValue("foo");

bar2.setValue("foo");
return foo;

}
```

not

```java
public static String doStuffAndGetFoo() {

bar.setValue("foo");
bar.setValue("foo");
bar2.setValue("foo");

return foo;

}
```

* Spaces with if-else blocks (and basically spaces in general)

e.g.

```java
if (foo == bar) {

//Do something

} else {

//Do something else

}
```

not

```java
if (foo==bar) {
//Do something
} else {
//Do something else
}
```

* Just be smart and look at some of my code if you have a question

##Using my code in your program

If you want to use my code in your program, that's great! I do ask that you **please** give me credit. To start off, your going to need to add [Apache Commons Collections](http://commons.apache.org/proper/commons-collections/), and [Appache Commons IO](http://commons.apache.org/proper/commons-io/) to your build path. Then you can add [my developer jar](../master/dev/midr-1.0.9.jar) to your build path. From there, my "libraries" are pretty straightforward (most of the time...), but I've still included a (poorly written) Javadoc which is avliable [here](http://ss111.github.io/midr-doc/). In addition, below is a basic example of how to find and resolve ID conflicts.

You will first want to call:

```java
ConfigHelper.populateMaps("/path/to/configuration/directory");
```

This will populate [MultiValueMap](http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html)s (one for blocks, items, and unknown IDs [IDs that may or may not be blocks/items]) with every item ID found in the configuration directory, and [ArrayList](http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html)s containing the IDs corrosponding name(s), and which configuration file(s) it was found in.

It can be visualized somewhat like this:

```
Key: 1337

Value(s): ["awesomeBlock", "awesomeConfig.cfg"], ["conflictingBlock", otherConfig.cfg"]
```

So, the ID 1337 has 2 (in this case) blocks mapped to it.

From there, you can retrieve these maps by doing:

```java
MultiValueMap blockMap = ConfigHelper.getBlockIDs();
MultiValueMap itemMap = ConfigHelper.getItemIDs();
MultiValueMap unknownMap = ConfigHelper.getUnknownIDs();
```

By iterating through one of these maps, you can then determine if an ID is conflicting or not by doing:

```java
for (Object key : blockMap.keySet()) {

	ID = Integer.valueOf(key.toString());

	if (ConflictHelper.isConflicting(blockMap, ID) == true) {
	//Optionally, you can also pass the type parameter to automatically store conflicting items in an ArrayList
	//if (ConflictHelper.isConflicting(blockMap, ID, "BLOCK") == true) {

         	//Do some stuff here

         }

}
```

The above method will return true or false respectively. You can also optionally pass the type parameter (either BLOCK or ITEM. This *is* case sensitive!) and ConflictHelper will store the conflicting IDs in thier respective ArrayLists.

If you want conflicts to automatically be resolved, we now need to populate more ArrayLists that will contain the unused block and item IDs. It's as simple as calling:

```java
IdDumpHelper.populateUnusedIDs("/path/to/nei/id/dump/");
```

You can also get the unused block/item IDs by calling:

```java
ArrayList<Integer> unusedBlocks = IdDumpHelper.getUnusedBlockIDs();
ArrayList<Integer> unusedItems = IdDumpHelper.getUnusedItemIDs();
```

Finally, to resolve the conflicts it's as easy as calling:

```java
ConflictResolver.resolveConflicts("/path/to/configuration/directory", IdDumpHelper.getUnusedBlockIDs(), IdDumpHelper.getUnusedItemIDs(), ConflictHelper.getConflictingBlocks(), ConflictHelper.getConflictingItems());
```

##License

This software is licensed under the [GNU General Public License v3](http://www.gnu.org/licenses/gpl-3.0.html).

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/
