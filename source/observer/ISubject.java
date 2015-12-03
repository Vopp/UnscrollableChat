package observer;

/**
 * A ISubject is an observable in a observer pattern
 * 
 * @author Henrik Eliasson 
 */
public interface ISubject {

        /**
         * Adds an IObserver to this subject
         * @param observer The observer to add
         */
        public void addObserver(IObserver observer);

        /**
         * Remove an IObserver from this subject
         * @param observer The observer to remove
         * @return Returns true if the observer was found, and removed.
         */
        public boolean removeObserver(IObserver observer);

        /**
         * Update observers
         */
        public void updateObservers();
}
