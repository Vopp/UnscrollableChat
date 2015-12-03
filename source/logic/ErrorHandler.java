package logic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import logging.Logger;

/**
 * A ErrorHandler processes IErrors. This basic version does not attempt to recover from any thrown
 * exceptions.
 *
 * @author Henrik Eliasson
 */
public class ErrorHandler implements IErrorHandler {

        private final Collection<IError> ERRORS = new ArrayList<>();

        private Logger mLogger;

        /**
         * Constructs a ErrorHandler with a initial Logger
         *
         * @param logger A Logger to be used by this ErrorHandler
         */
        public ErrorHandler(Logger logger) {
                mLogger = logger;
        }

        /**
         * Constructs a ErrorHandler without a initial Logger
         */
        public ErrorHandler() {

        }

        @Override
        public Collection<IError> getErrors() {
                return ERRORS;
        }

        @Override
        public void addError(Exception e, boolean handled) {
                ERRORS.add(new Error(e, handled, LocalDateTime.now().toString()));
        }

        /**
         * Checks all Errors for un-handled flags. Terminates application and attempts to log if
         * found.
         *
         * As is here, Runtime objects are given a chance (if implemented) to handle Exceptions that
         * occur, while still storing the error. However, any handled errors will only be logged if
         * there is finally a un-handled Exception thrown. For example: if a class attempts to
         * correct an Exception but something goes wrong further down the line, that previously
         * handled error will still leave a trace in the log incase that handled error was somehow
         * the root cause.
         */
        @Override
        public void checkErrors() {
                boolean exit = false;
                for (IError e : ERRORS) {
                        if (!e.isHandled()) {
                                exit = true;
                        }
                }
                if (exit) {
                        try {
                                if (mLogger != null) {
                                        for (IError e : ERRORS) {
                                                //first log the the error
                                                mLogger.logEvent(e.getException().toString(), " - ERROR - ", e.getTimeStamp());

                                                //then the trace
                                                for (StackTraceElement s : e.getException().getStackTrace()) {
                                                        mLogger.logEvent(s.toString(), "          ", "");
                                                }
                                        }
                                }
                        }
                        catch (Exception e) {
                                //Do nothing, exiting anyway
                        }
                        System.exit(0);
                }
        }

        @Override
        public void setLogger(Logger logger) {
                mLogger = logger;
        }

        @Override
        public Logger getLogger() {
                return mLogger;
        }
}
