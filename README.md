# Advent Of Code Java Starter

This is starter code for Advent of Code using an abstract class to handle creating a `java.util.Scanner` and a  `java.io.BufferedReader` for the puzzle solver.

## How to use

[Day1.java](Day1.java) to [Day5.java](Day5.java) are included as starter code.  

Each file will run `part1()` and `part2()` and print the result.  
A `Scanner` called `scan` and a `BufferedReader` called `read` are available for each part, so that you can choose which you prefer.
Each program specifies a default input filename (currently Day1.input for day 1) in the main method.  

Each day extends the abstract class [AOCAbstract.java](AOCAbstract.java). If you make helper methods for a particular day. Consider moving them up to the abstract class if they have the potential for being useful in future days.


To make Day6.java etc., you can duplicate Day1.java change the number to the day you want and replace all instances of Day1 with Day*Number* where *Number* is the new day number that you are making. It's easiest if you duplicate the file before you fill in the new code. 


## to compile and run

To compile [Day1.java](Day1.java) make sure you are in the same folder that this file is in and use the command:

```sh
javac Day1.java
```

To run the program after compiling, make sure you are in the same folder that this file is in (or your classpath has this folder in it) and use the command:

```sh
java Day1
```