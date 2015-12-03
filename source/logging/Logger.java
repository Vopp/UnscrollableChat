
package logging; 

import observer.IObserver;

/** 
 * Logs data
 * 
 * @author Henrik Eliasson 
 */ 
public abstract class Logger implements IObserver { 
        
        /**
        * Logs the current event
        * 
        * @param event A description of the event
        * @param prefix Used as title, for example
        * @param timeStamp The time for when the log event occurred
        * @throws java.lang.Exception Dependant on implementation
        */
        public abstract void logEvent(String event, String prefix, String timeStamp) throws Exception;
        
        /**
         * Sets the current state of this Logger
         * 
         * @param state boolean describing if the Logger should be processing, or not.
         */
        public abstract void setOnOFF(boolean state);
        
        /**
         * Retrieves the current state of this Logger
         * 
         * @return boolean describing if the Logger is currently switched on or not
         */
        public abstract boolean getState();
       
} 
