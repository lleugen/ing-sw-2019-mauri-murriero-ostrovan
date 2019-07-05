This project implements the following
"regole complete + CLI + RMI"

To launch the jar as a server
java -jar -Djava.security.policy=policy adrenalina.jar type=server host=<server IP or FQDN> lobbyTimeout=<seconds before closing an incomplete room> disconnectionTimeout=<seconds to wait for getting a response from an user>

To launch the jar as a client
java -jar -Djava.security.policy=policy adrenalina.jar type=client host=<server IP or FQDN> ui=cli

NOTE: The jar file and the policy files must be located in the same folder, and they must be located in the folder the two commands are being launched in