package gui;

import logic.ChatCommunicator;
import observer.ISubject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import logic.IError;

/**
 * A simple GUI module for a chat application. Featuring unscrollability and a smashed button.
 *
 * @author Thomas Ejnefjäll, Henrik Eliasson
 */
public class UnscrollableGUI extends ChatGUI {

        private static final long serialVersionUID = -6901406569465760897L;
        private JTextArea mChatArea, mMessageArea;
        private JButton mSendButton;
        private ChatCommunicator mCommunicator;
        private String mUser;

        /**
         * Creates a UnscrollableGUI
         *
         * @param userName the name to use in the chat
         * @param com A communications object used for sending data, according to a transport layer
         * protocol
         */
        public UnscrollableGUI(String userName, ChatCommunicator com) {
                this.setTitle("Unscrollable Chat - " + userName);
                mUser = userName;
                mCommunicator = com;
                this.initializeGUI();
                this.addGUIListeners();
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        /**
         * Initializes the GUI
         */
        private void initializeGUI() {
                mChatArea = new JTextArea(25, 1);
                mMessageArea = new JTextArea(3, 10);
                mSendButton = new JButton("Send");

                mMessageArea.setLineWrap(true);
                mMessageArea.setBorder(BorderFactory.createLineBorder(Color.black));
                mChatArea.setEnabled(false);
                mChatArea.setLineWrap(true);

                Container contentPane = this.getContentPane();

                contentPane.add(mChatArea, BorderLayout.NORTH);
                contentPane.add(mMessageArea, BorderLayout.WEST);
                contentPane.add(mSendButton, BorderLayout.CENTER);

                this.setSize(350, 500);
                this.setResizable(false);
        }

        /**
         * Adds GUI related listeners
         */
        private void addGUIListeners() {
                mSendButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                sendMessage();
                        }
                });
                mMessageArea.addKeyListener(new KeyAdapter() {
                        public void keyReleased(KeyEvent e) {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                        mMessageArea.setText(mMessageArea.getText().substring(0, mMessageArea.getText().length() - 1));
                                        sendMessage();
                                }
                        }
                });
        }

        @Override
        public void sendMessage() {
                try {
                        if (mMessageArea.getText().length() > 0) {
                                mCommunicator.sendChat(mUser, mMessageArea.getText());
                                mMessageArea.setText("");
                        }
                        mMessageArea.grabFocus();
                }
                catch (Exception e) {
                        mCommunicator.getErrorHandler().addError(e, false);
                }
        }

        @Override
        public void printMessage(String message) {
                mChatArea.append(message + "\n");

        }

        @Override
        public void displayError(String title, String description) {
                JOptionPane.showMessageDialog(this, description, title, JOptionPane.WARNING_MESSAGE);
        }

        @Override
        public void update(ISubject subject) {
                ChatCommunicator com = (ChatCommunicator) subject;
                if (!com.getErrorHandler().getErrors().isEmpty()) {
                        StringBuilder SB = new StringBuilder();
                        for (IError e : com.getErrorHandler().getErrors()) {
                                if (!e.isHandled()) {
                                        SB.append(e.getException().toString()).append("\n");
                                }
                        }
                        displayError("Error", SB.toString());
                }
                if (!com.getMessage().isEmpty()) {
                        printMessage(com.getMessage());
                }
        }
}
