package logic;

/**
 * An IError handles contains and describes the status for an Exception
 * 
 * @author Henrik Eliasson
 */
public interface IError {

        /**
         * Sets the status for this Error
         *
         * @param handled boolean describing if the wrapped exception has been handled or not
         */
        public void setHandled(boolean handled);

        /**
         * Retrieves status for this Error
         *
         * @return boolean describing if the wrapped exception has been handled or not
         */
        public boolean isHandled();

        /**
         * Retrieves the Exception for this Error
         *
         * @return The Exception this ExceptionError wraps
         */
        public Exception getException();

        /**
         * Retrieves the timestamp for this Error
         *
         * @return A String describing when this Error occurred
         */
        public String getTimeStamp();

}
