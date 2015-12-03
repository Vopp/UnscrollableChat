package gui;

import javax.swing.JFrame;
import observer.IObserver;

/**
 * A ChatGUI defines base functionality for a GUI used by the chat while
 * taking the role of an observer, in the "observer pattern".
 * 
 * @author Henrik Eliasson
 */
public abstract class ChatGUI extends JFrame implements IObserver {

        /**
         * Send message to all users
         */
        public abstract void sendMessage();

        /**
         * Display a message from a user
         *
         * @param message The received message
         */
        public abstract void printMessage(String message);
        
        /**
         * Notify a user that an error occurred, and why
         *
         * @param message The string informing a user of the occurred error
         * @param exception The Exception that prompted the error message, as a String
         */
        public abstract void displayError(String message, String exception);        
}
