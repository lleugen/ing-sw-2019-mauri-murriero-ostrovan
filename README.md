### Software engineering course project (Politecnico di Milano 2019)
![Adrenaline : game cover](https://geekandsundry.com/wp-content/uploads/2016/11/adrenalinecover.jpg)

#### [Adrenaline](https://czechgames.com/en/adrenaline/) implementation (java 8)

#### Overview
This project implements the complete rules of the game[ {1} ](https://czechgames.com/files/rules/adrenaline-rules-en.pdf)[ {2} ](https://czechgames.com/files/rules/adrenaline-rules-weapons-en.pdf) with a command line interface using RMI for client-server communication.

The [Deliveries](/Deliveries) folder contains JAR file, UML diagrams, Sonarqube reports and Javadoc.

The [src](/src) folder contains source code and unit tests.

The [policy](/policy) file specifies security permissions for RMI.

This was mostly a learning exercise and the final project does not satisfy optimal production quality requirements.

#### Known issues and possible improvements
* The architecture could be better, the game flow is entirely controller by the server and clients can only act when prompted.
* GUI interface can be added and CLI interface could be rendered more interactive and pretty.
* There are a few known bugs on some of the weapons.
* More functionalities can be added, such as multiple matches on one server or game saving.

#### Requirements
The game requires Java 8 or later versions to run.

#### Launch instructions
rmiregistry doesn't have to be launched manually, it is started automatically
To launch the jar as a server
```sh
$ java -jar -Djava.security.policy=policy adrenalina.jar type=server host=\<server IP or FQDN\> lobbyTimeout=\<seconds before closing an incomplete room\> disconnectionTimeout=\<seconds to wait for getting a response from a user\>
```
  
e.g.:
```sh
$ java -jar -Djava.security.policy=policy adrenalina.jar type=server host=localhost lobbyTimeout=15 disconnectionTimeout=180
```

To launch the jar as a client
```sh
$ java -jar -Djava.security.policy=policy adrenalina.jar type=client host=\<server IP or FQDN\> ui=cli
```

e.g.:
```sh
$ java -jar -Djava.security.policy=policy adrenalina.jar type=client host=localhost ui=cli
```

NOTE : The jar file and the policy files must be located in the same folder, and they must be located in the folder the two commands are being launched in

#### Developers
* [Eugenio Ostrovan](https://github.com/lleugen)
* [Fabio Mauri](https://github.com/cripty2001)
* [Riccardo Murriero](https://github.com/reymurry)

##### This project is licensed under the terms of the MIT license.
