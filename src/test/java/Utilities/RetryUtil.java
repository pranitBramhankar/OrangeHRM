package Utilities;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic retry utility. Use to retry operations that can throw transient exceptions
 * such as StaleElementReferenceException. Supports retrying on multiple
 * exception types.
 */
public class RetryUtil {
    private static final Logger LOGGER = Logger.getLogger(RetryUtil.class.getName());

    @SafeVarargs
    public static <T> T retryOnException(Supplier<T> operation, int maxAttempts, long delayMillis,
            Class<? extends Throwable>... retryOn) {
        int attempts = 0;
        while (true) {
            try {
                return operation.get();
            } catch (Throwable e) {
                attempts++;
                boolean shouldRetry = isInstanceOfAny(e, retryOn);
                if (!shouldRetry || attempts >= maxAttempts) {
                    // rethrow preserving runtime exceptions
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    }
                    throw new RuntimeException(e);
                }
                LOGGER.log(Level.FINE, "Retry attempt " + attempts + " due to: " + e.getClass().getSimpleName(), e);
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
            }
        }
    }

    @SafeVarargs
    public static void retryOnException(Runnable operation, int maxAttempts, long delayMillis,
            Class<? extends Throwable>... retryOn) {
        retryOnException(() -> {
            operation.run();
            return null;
        }, maxAttempts, delayMillis, retryOn);
    }

    private static boolean isInstanceOfAny(Throwable e, Class<? extends Throwable>[] types) {
        if (types == null || types.length == 0) {
            return false;
        }
        for (Class<? extends Throwable> t : types) {
            if (t.isInstance(e)) {
                return true;
            }
        }
        return false;
    }
}
