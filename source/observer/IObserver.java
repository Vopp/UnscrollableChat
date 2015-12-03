package observer;

/**
 * An IObserver receives updates from a ISubject
 * 
 * @author Henrik Eliasson 
 */
public interface IObserver {

        /**
         * Updates this IObserver with changes, individual IObserver response will vary.
         * @param subject The subject triggering the update
         */
        public void update(ISubject subject);
}
