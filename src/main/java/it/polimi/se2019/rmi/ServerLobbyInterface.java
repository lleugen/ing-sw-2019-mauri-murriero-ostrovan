package it.polimi.se2019.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerLobbyInterface extends Remote {
    /**
     * Make the connection to the server
     * The server will lookup for an RMI registry on the ip the
     * client used to connect (see {@link #getIp()}), on the port
     * passed as parameter, at the reference passed as parameter.
     *
     * @param ref  The reference the server should use to retrieve a view to
     *             interact with.
     * @param port Port the registry is exposed on
     *
     * @throws RemoteException If something goes wrong with RMI
     */
    void connect(String ref, int port) throws RemoteException;

    /**
     * @return The ip (viewed by the server) of the connecting client. This
     *         can then be used to properly create an RMI Registry.
     *         Null if for some reason the ip can not retrived
     * @throws RemoteException If something goes wrong with RMI
     */
    String getIp() throws RemoteException;
}
