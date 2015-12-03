package logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Set;
import logging.Logger;
import observer.IObserver;

/**
 * A UDPCommunicator handles the network traffic between all of it's chat clients (OBSERVERS).
 * Messages are sent and received via the UDP protocol which may lead to messages being lost.
 *
 * @author Thomas Ejnefjäll, Henrik Eliasson
 */
public class UDPCommunicator extends ChatCommunicator {

        private final int DATAGRAM_LENGTH = 100;
        private final int PORT = 6789;
        private final String MULTICAST_ADDRESS = "228.28.28.28";
        private final Set<IObserver> OBSERVERS;
        private final IErrorHandler ERROR_HANDLER;
        private String mMessage = "<start session>";

        /**
         * Creates a ChatCommunicator
         *
         * @param log The error logger used to log exceptions
         */
        public UDPCommunicator(Logger log) {
                ERROR_HANDLER = new ErrorHandler(log);
                OBSERVERS = new HashSet<>();
        }

        @Override
        public void sendChat(String sender, String message) throws IOException {

                DatagramSocket socket = new DatagramSocket();
                String toSend = sender + ": " + message;
                byte[] b = toSend.getBytes();

                DatagramPacket datagram = new DatagramPacket(b, b.length,
                        InetAddress.getByName(MULTICAST_ADDRESS), PORT);

                socket.send(datagram);
                socket.close();
        }

        @Override
        public void startListen() {
                new Thread(this).start();
        }

        /**
         * Listens for messages from other clients
         *
         * @throws IOException If there is an IO error
         */
        private void listenForMessages() throws IOException {
                byte[] b = new byte[DATAGRAM_LENGTH];
                DatagramPacket datagram = new DatagramPacket(b, b.length);

                try (MulticastSocket socket = new MulticastSocket(PORT)) {
                        socket.joinGroup(InetAddress.getByName(MULTICAST_ADDRESS));
                        while (true) {
                                socket.receive(datagram);
                                String message = new String(datagram.getData());
                                mMessage = message.substring(0, datagram.getLength());
                                updateObservers();
                                datagram.setLength(b.length);
                        }
                }
        }

        @Override
        public void run() {
                try {
                        this.listenForMessages();
                }
                catch (IOException e) {
                        ERROR_HANDLER.addError(e, false);
                        updateObservers();
                }
        }

        @Override
        public void addObserver(IObserver o) {
                OBSERVERS.add(o);
        }

        @Override
        public boolean removeObserver(IObserver o) {
                return OBSERVERS.remove(o);
        }

        @Override
        public void updateObservers() {
                OBSERVERS.forEach((o) -> {
                        o.update(this);
                });
                ERROR_HANDLER.checkErrors();
        }

        @Override
        public String getMessage() {
                return mMessage;
        }

        @Override
        public IErrorHandler getErrorHandler() {
                return ERROR_HANDLER;
        }
}
