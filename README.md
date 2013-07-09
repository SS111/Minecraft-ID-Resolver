#Minecraft ID Resolver - A modpack creators best friend!

Welcome! I have created a tool which I believe both normal users and modpack creators will be able to utilize. It is written in Java and therefore will work on every OS and is designed to find and fix Minecraft ID conflicts.

#Usage

1. Select the configuration directory
2. Search for conflicts
3. Manually resolve conflicts by using the list of conflicts the program provides

Please note that the unknown IDs tab is an attempt to support unsupported configuration files (aka any non-forge configuration file) and is **most likely always** going to be a false positive. Because of this, this tool *will not* attempt to resolve unknown ID conflicts.

or, to resolve ID conflicts automatically

1. Select the configuration directory
2. Search for conflicts
3. Provide an [NEI](http://www.minecraftforum.net/topic/909223-147152-smp-chickenbones-mods/) ID dump
 1. To do this, first start Minecraft with NEI installed.
 2. Open any world
 3. Open your inventory
 4. Click "Options"
 5. Click "Block/Item ID Settings"
 6. Make sure that item IDs are dumped! It **will not** dump item IDs by default!
 7. Click "Dump ID Map Now". This will dump the ID map to your .minecraft folder. Now just browse to the file when asked to provide a NEI ID dump.
4. Allow the program to resolve conflicts automatically

If for whatever reason your configuration is messed up afterwards, there will be a backup in your .minecraft directory named config_bak.

#Building from source

If you just want a stable build, grab the latest version from the [releases page](https://github.com/SS111/Minecraft-ID-Resolver/releases).

However, if you want the bleeding edge build first clone the repository. Then you are going to need to add [MiG Layout](http://www.miglayout.com/), [Apache Commons Collections](http://commons.apache.org/proper/commons-collections/), and [Appache Commons IO](http://commons.apache.org/proper/commons-io/) to the build path. Finally, make sure that the classpath is set to WindowMain and then compile.

#Contributing

Contributing is a great way to help me and the community out. Everything helps!

##Dontaing 

Donating is one way to express your thanks for the work I do in my free time. You can donate any amount [here](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=DBT788PS4EN8J).

##Pull requests

Contirbuting to my code is another wonderful way to help me out. I will accept just about any pull requests.

However, I do ask that:

1. Don't make any readme changes unless its of *vast importance*
2. Don't just change a variable or function name and call it a pull request. Please only commit changes that add new content or changes something that is *very important*
3. Follow my coding format

###My coding format

When making a pull request, I ask that you follow a few simple format rules that I use so that even in the future the code still flows nicely.

Rules:

+ Please keep the first bracket on the opening line

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

+ Keep spaces when performing actions on different variables

e.g.

```java
public static void foo() {

bar.setValue("something else");
bar.setValue("something else");

bar2.setValue("foo");

}
```

not

```java
public static void foo() {

bar.setValue("something else");
bar.setValue("something else");
bar2.setValue("foo");

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

#Using my code in your program

If you want to use my code in your program, that's great! I do ask that you **please** give me credit. To start off, your going to need to add [Apache Commons Collections](http://commons.apache.org/proper/commons-collections/), and [Appache Commons IO](http://commons.apache.org/proper/commons-io/) to your project. From there my "libraries" are pretty straightforward (most of the time...) but I'll give examples anyway.

You will first want to call

```java
ConfigHelper.populateMap("/path/to/configuration/directory");
```

This will populate [MultiValueMap](http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html)s with every item ID found in the configuration directory and it's corrosponding name(s).

From there, you can retrieve these maps by doing:

```java
MultiValueMap myMap = ConfigHelper.getBlockIDs();
MultiValueMap mySecondMap = ConfigHelper.getItemIDs();
```

By iterating through this map, you can then determine if an ID is conflicting or not by doing:

```java
for (Object key: myMap) {

ID = Integer.valueOf(key.toString());

if (ConflictHelper.isConflicting(myMap, ID, "BLOCK") == true) {

         //Do some stuff here

         }

}
```

The above method will return true or false respectively. It is also very important to pass the type parameter (either BLOCK, ITEM, or UNKNOWN) because ConflictHelper will automatically store conflicting values in thier respective ArrayList. (i.e there is an ArrayList for conflicting blocks and for conflicting items).

If you want conflicts to automatically be resolved, we now need to populate more ArrayLists that will contain the unused block and item IDs. It's as simple as calling:

```java
IdDumpHelper.populateUnusedIDs("/path/to/nei/id/dump/");
```

You can get the unused block/item IDs by calling:

```java
ArrayList<Integer> myList = IdDumpHelper.getUnusedBlockIDs();
ArrayList<Integer> mySecondList = IdDumpHelper.getUnusedItemIDs();
```

Finally, to resolve the conflicts you can simply do:

```java
ConflictResolver.resolveConflicts("/path/to/configuration/directory", IdDumpHelper.getUnusedBlockIDs(), IdDumpHelper.getUnusedItemIDs(), ConflictHelper.getConflictingBlocks(), ConflictHelper.getConflictingItems());
```

#License

This software is licensed under the [GNU General Public License v3](http://www.gnu.org/licenses/gpl-3.0.html).

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/
