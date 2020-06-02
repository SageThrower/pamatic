#Pamatic
Pamatic is an easy to understand and to use Fawe api to paste schematics

I made this because i found the Fawe api very clunky and i wasted about a day trying to paste a single schematic and then relighting it afterwards

If you want to use this then go ahead, it probably wont work in the next fawe version but... :)


##Usage
There are no maven/gradle repos, import it statically.

If you are using maven here is the dependency if you do not know how to import them statically
```
<dependency>
    <groupId>tech.sodiium</groupId>
    <artifactId>pamatic</artifactId>
    <version>latest</version>
    <scope>system</scope>
    <!-- here you need to add the path to the jar, ${project.basedir} is the base directory of the project -->
    <systemPath>${project.basedir}/your/own/libs/dir/pamatic.jar</systemPath> 
</dependency>
```

You need to import `tech.sodiium.pamatic.SchematicManager`

Now you have access to some methods that will probably be all you need

- `pasteSchematic(File schematicFile, int x,  y, int z, World world)`<br />
Pastes a schematic at the given coordinates in the given world
If the schematicFile does not exist an IOException will be thrown


- `pasteSchematic(File schematicFile, Location location)` <br />
Exactly the same as above but uses a Location instead of 30k parameters

There are also Clipboard methods that utilize a Clipboard object instead of a File object but since you are using this api to get as far away from Fawe as you can i do not think you will use those
