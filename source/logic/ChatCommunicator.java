package logic;

import observer.ISubject;

/**
 * The communicator handles the network traffic between all chat clients 
 * It takes the role of a subject, in the "observer pattern".
 *
 * @author Henrik Eliasson
 */
public abstract class ChatCommunicator implements Runnable, ISubject {

        /**
         * Sends the chat message to all clients
         *
         * @param sender Name of the sender
         * @param message Text message to send
         * @throws Exception Dependant on implementation
         */
        public abstract void sendChat(String sender, String message) throws Exception;

        /**
         * Starts to listen for messages from other clients
         */
        public abstract void startListen();

        /**
         * Retrieves the last message
         *
         * @return Message held by the communicator
         */
        public abstract String getMessage();
        
        /**
         * Retrieves all Errors
         * 
         * @return The IOException that occurred
         */
        public abstract IErrorHandler getErrorHandler();
}
