package logic;

/**
 * A Error wraps an Exception with a flag signaling if the Exception has been handled or not.
 *
 * @author Henrik Eliasson
 */
public class Error implements IError {

        private Exception mException;
        private boolean mHandled;
        private String mTimeStamp;
        
        /**
         * Constructs a new Error around an exception
         *
         * @param e The Exception to wrap
         * @param handled boolean indicating if the Error has been handled or not
         * @param description The time for when the Exception occurred
         */
        public Error(Exception e, boolean handled, String description) {
                mException = e;
                mHandled = handled;
                mTimeStamp = description;
        }
        
        @Override
        public void setHandled(boolean handled) {
                mHandled = handled;
        }

        @Override
        public boolean isHandled() {
                return mHandled;
        }

        @Override
        public Exception getException() {
                return mException;
        }

        @Override
        public String getTimeStamp() {
                return mTimeStamp;
        }

}
