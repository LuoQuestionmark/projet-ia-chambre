# projet-ia-chambre
This is the repository for my school project: "Création d’un agent aspirateur".
The code (should be) written in Java, elaborating some concepts of AI and multithreading.

Wenhao Start at 22 Sep, at Chicoutimi.

## note from the class

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
