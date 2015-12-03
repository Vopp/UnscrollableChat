package logging;

import logic.ChatCommunicator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import observer.*;

/**
 * A TextFileLogger saves messages to file (same location as the executable).
 *
 * @author Henrik Eliasson
 */
public class TextFileLogger extends Logger {

        private static final StringBuilder SB = new StringBuilder();
        private final String mFileName;
        private boolean mState;

        /**
         * Constructs a new TextFileLogger and sets its state to "on" (true)
         *
         * @param fileName The name of the log file written to location
         */
        public TextFileLogger(String fileName) {
                mFileName = fileName;
                mState = true;
        }

        @Override
        public void update(ISubject s) {
                if (mState) {
                        ChatCommunicator com = (ChatCommunicator) s;
                        try {
                                logEvent(com.getMessage(), " - ", LocalDateTime.now().toString());
                        }
                        catch (IOException e) {
                                com.getErrorHandler().addError(e, false);
                        }
                }
        }

        @Override
        public void logEvent(String event, String prefix, String timeStamp) throws IOException {
                SB.append(timeStamp);
                SB.append(prefix);
                SB.append(event);

                writeLine(SB.toString());

                SB.setLength(0);
        }

        /**
         * Writes the current event as a line in the log file
         *
         * @param line The current line to append to the log
         * @throws java.io.IOException
         */
        private void writeLine(String line) throws IOException {
                PrintWriter pw = new PrintWriter(new FileWriter(mFileName + ".txt", true));
                pw.println(line);
                pw.close();
        }

        @Override
        public void setOnOFF(boolean state) {
                mState = state;
        }

        @Override
        public boolean getState() {
                return mState;
        }
}
