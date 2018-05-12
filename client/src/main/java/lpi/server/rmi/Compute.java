package lpi.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author RST Defines the operations provided by the server.
 */
public interface Compute extends Remote {

    /**
     * The name of the server remote object in the server's registry.
     */
    public static final String RMI_SERVER_NAME = "lpi.server.rmi";

    /**
     * Simplest method that does not accept any parameters and does not return
     * any result. The easiest way to ensure everything works as expected.
     *
     * @throws RemoteException in case of communication issues.
     */
    String ping() throws RemoteException;

    /**
     * Next method to test client-server communication and parameter passing.
     *
     * @param text Any text you want to send to the server.
     * @return The text you sent prepended with the "ECHO:".
     * @throws RemoteException in case of communication issues.
     */
    String echo(String text) throws RemoteException;

    /**
     * Allows logging in the user.
     *
     * @param task The task information that should be executed to the server.
     * @return Result executing the task.
     * @throws RemoteException in case of communication issues.
     * @throws ServerException if the server failed to process the request.
     */
    <T> T executeTask(Task<T> task) throws ArgumentException, ServerException, RemoteException;

    public static class ServerException extends RemoteException {
        private static final long serialVersionUID = 2592458695363000913L;

        public ServerException() {
            super();
        }

        public ServerException(String message, Throwable cause) {
            super(message, cause);
        }

        public ServerException(String message) {
            super(message);
        }
    }

    public static class ArgumentException extends RemoteException {
        private static final long serialVersionUID = 8404607085051949404L;

        private String argumentName;

        public ArgumentException() {
            super();
        }

        public ArgumentException(String argumentName, String message, Throwable cause) {
            super(message, cause);
            this.argumentName = argumentName;
        }

        public ArgumentException(String argumentName, String message) {
            super(message);
            this.argumentName = argumentName;
        }

        /**
         * Gets the name of the argument that did not pass validation.
         *
         * @return A name of the argument that was not valid.
         */
        public String getArgumentName() {
            return argumentName;
        }
    }
}
