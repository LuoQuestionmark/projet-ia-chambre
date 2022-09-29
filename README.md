# projet-ia-chambre
This is the repository for my school project: "Création d’un agent aspirateur".
The code (should be) written in Java, elaborating some concepts of AI and multithreading.

Wenhao Start at 22 Sep, at Chicoutimi.

## Introduction of project

This project Java introduce a model containing a room with a cleaning robot, and various components of the robot. By using the BDI system and several strategies of exploration, the robot is capable of travailing through the room, doing actions to keep the room "clean".

### Strcture of project

All the source code are put in the `./src` repository. This program follow the OOP paradigm, thus each part of the code are written in a seperated class and put into a seperated sub-repository. The organization of files are the following:
```
src
|-muUtil // the Vector class, (int x, int y)
|-Robot  // the Robot
||-Actions // possible actions, like Move()
||-Beliefs // the knowledge of current situation
||-Desires // evaluation functions 
||-Search  // strategies, e.g. BFS
||-Cleaner // interact with the Room
||-Robot   // main class of robot
||-Vision  // interact with the Room
|-Rooms  // the Room
||-Cell  // a "pièce" of the room
||-Room  // combination of mutiple cells
|-Tests  // the tests
```

### Multithreading

As demanded, the project is running on multithread, based on the default implementation of Java's multithreading. The class `Room` and `Robot` are implemented with the interface `Runnable`, which make them running individually along side with the main thread of one of the `Test` classes.

To make the program more human friendly, each thread contains a `sleep()` function, so basically each part update once per second. Variables like `isRunning` are implemented, while without a real way to modify its value.

### The `Room` class
One `Room` is hardcoded with 25 parts. Each part can contain either or both of "dirt" or "jewel". Some other stats are also stored inside the class, like `dirtCleaned` counting how many dirts have been vacuumed. To show its status in an interactive way, there is a method called `printRoom` to print the result in the console.

Another feature of this class is that it's capable of generating objects thoughout time automatically. The program use the system time as a reference, and the number of dirts and of jewels generated is hardcoded inside the class. To turn on this function, one need to call the method `setAutoGenerate`.

### The `Robot` class
The `Robot` class contains many components to simulate the scenario. 

`Actions` contains the actions like `Move` or `Clean`, which will call the methods in the class `Robot`, which will then call the `Cleaner`, which will call the methods in the `Room` class.

`Belief` contains the observation / the inference of the world (`Room`), including the details inside a room, and some properties of the world. One `Belief` can be generated by the `Vision` (observation), or another existing `Belief` and a new action ; the corresponding method is `Belief.act`, to some extends, it is a "meta-belief" of the world.

Each `Desire` basically is the container of an evaluation function. To see if the world is "clean".

`Search` contains various searching strategies ("informé", "non informé"). It can generate a list of `Action` based on a `Belief` and a `Desire`.

## Run the code

All the code are written with functions in the standard libraries, so no extra import is needed. To compile and to execute the program, use the general methods of `javac` and `java` in a console.

There are four `Test` classes in the `./Tests` repository. `Test` and `Test2` are show cases of the implentations, while `Test3` and `Test4` contains more complicated examples. Notice that all the information is printed in the console.

## Appendix: notes
### note from the lecture

```
while alive:
    belief = observe(env)     # this is not at real time
                              # belief = random(5, 5)
    init_problem_form(desire) # desire =  zeros(5, 5)
    
    if intent or nb_act_optimal: # either there is no more action 
                                 # or there are enough actions
        intent = search(p)
    act(intent[0])
```

### note from myself

*This section will be the note from my thinking before actually writting the code.*

- Room: Done. I have already a module which is capable of creating a room with 25 pieces, and update them with an interface with the robot.
- Robot: Partially. The robot can execute actions, yet it doesn't have a strategy so that it doesn't know what to do. I also need a way to print its status, like a printStatus function, maybe a log file.

The next step is to program a strategy for the bot. 
- Belief: the status of the room, plus some rules like:
    - the gain of doing certain action, e.g. take a jewel
- Desire: this will be hard. The robot "want" the room to be clean, but code this desire as code is very abstract. And we need the "stratégie informé" et "non-informé". So at this moment my plan is to make a ```Function``` object which check is the room is clean for the "non-informé" version, and another ```Function``` object that do the calculation based on how much dirt and jewel in the room.
- Intention: well a suite of action as far as I know. Maybe I need an interface called "action" and pack these action inside.
