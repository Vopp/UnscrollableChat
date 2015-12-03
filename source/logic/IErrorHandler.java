
package logic; 

import java.util.Collection;
import logging.Logger;

/** 
 * A ErrorHandler processes IErrors for logging.
 * 
 * @author Henrik Eliasson 
 */ 
public interface IErrorHandler {         
        /**
         * Returns a list containing all errors from this session
         * 
         * @return A List containing IErrorHandler objects
         */
        public Collection<IError> getErrors();

        /**
         * Add a new Error to the list of Errors
         * 
         * @param e The Exception to add
         * @param handled boolean describing if the exception is handled or not
         */
        public void addError(Exception e, boolean handled);

        /**
         * Checks all Errors
         */
        public void checkErrors();
        
        /**
         * Sets a new Logger for this ErrorHandler to use
         * 
         * @param logger 
         */
        public void setLogger(Logger logger);
        
        /**
         * Retrieves the current Logger
         * 
         * @return The Logger currently used by this ErrorHandler
         */
        public Logger getLogger();
} 
