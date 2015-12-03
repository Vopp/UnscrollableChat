

import gui.UnscrollableGUI;
import gui.ChatGUI;
import javax.swing.JOptionPane;
import logging.TextFileLogger;
import logic.ChatCommunicator;
import logic.UDPCommunicator;

/**
 * Program entry point. A simple chat application that lets you chat with all others using the same
 * application. In this version there is only one global channel and users can not create or join
 * other channels.
 *
 * @author Thomas Ejnefjäll, Henrik Eliasson
 * @version 0.1
 */
public class ChatMain {        
        /**
         * Program stem and entry point.
         *
         * @param args a name to use in the chat, if no name is provided via main the program will
         * prompt the user for one
         */
        public static void main(String[] args) {
                String userName = "";

                if (args.length > 0) {
                        userName = args[0];
                }
                while (userName == null || userName.length() < 1) {
                        userName = JOptionPane.showInputDialog(null, "Enter your name", "Name", JOptionPane.QUESTION_MESSAGE);
                }
                
                //In order to utilize the ChatGUI, ChatCommunicator and Logger modularity: a solution for a settings screen or
                //the like would need to replace the hard coded classes used below. Also, new modules need to be made to give alternative!
                ChatCommunicator com = new UDPCommunicator(new TextFileLogger("errorlog"));
                
                ChatGUI gui = new UnscrollableGUI(userName, com);
                gui.setVisible(true);

                com.startListen();
                com.addObserver(gui);
                com.addObserver(new TextFileLogger("chatlog"));          
        }       
}
