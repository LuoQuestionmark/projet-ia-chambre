# projet-ia-chambre
This is the repository for my school project: "Création d’un agent aspirateur".
The code (should be) written in Java, elaborating some concepts of AI and multithreading.

Wenhao Start at 22 Sep, at Chicoutimi.

## note from the lecture

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

## note from myself

*This section will be the note from my thinking before actually writting the code.*

- Room: Done. I have already a module which is capable of creating a room with 25 pieces, and update them with an interface with the robot.
- Robot: Partially. The robot can execute actions, yet it doesn't have a strategy so that it doesn't know what to do. I also need a way to print its status, like a printStatus function, maybe a log file.

The next step is to program a strategy for the bot. 
- Belief: the status of the room, plus some rules like:
    - the gain of doing certain action, e.g. take a jewel
- Desire: this will be hard. The robot "want" the room to be clean, but code this desire as code is very abstract. And we need the "stratégie informé" et "non-informé". So at this moment my plan is to make a ```Function``` object which check is the room is clean for the "non-informé" version, and another ```Function``` object that do the calculation based on how much dirt and jewel in the room.
- Intention: well a suite of action as far as I know. Maybe I need an interface called "action" and pack these action inside.
