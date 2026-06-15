package Utilities;

import com.google.common.base.Supplier;

public class TestRetryUtil {

	public static<T> T retryOnException(Supplier<T> operation, int maxAttempts, Class<? extends Throwable>...retryOn) {
		
		int attempts = 0;
		
		while(true) {
			try {
				return operation.get();
			}catch(Throwable e) {
				boolean shouldRetry = isInstanceofAny(e,retryOn);
				attempts++;
				
				if(!shouldRetry || attempts<=maxAttempts) {
					
					if(e instanceof RuntimeException) {
						throw (RuntimeException) e;
					}
					
					throw new RuntimeException(e);
				}
			}
		}	
	}
	
	public static void retryOnException(Runnable operation, int maxAttempts, Class<? extends Throwable>...retryOn) {
		int attempts = 0;
		while(true) {
			attempts++;
			
			try {
				operation.run();
				return;
			}catch(Throwable e) {
				
				boolean shouldRetry = isInstanceofAny(e,retryOn);
				if(!shouldRetry || attempts<=maxAttempts) {
					throw (RuntimeException) e;
				}
				throw new RuntimeException(e);
			}	
		}
	}
	
	private static boolean isInstanceofAny(Throwable e, Class<? extends Throwable>[] passedExceptions) {
		
		if(passedExceptions == null || passedExceptions.length == 0) {
			return false;
		}
		
		for(Class<? extends Throwable> t : passedExceptions) {
			if(t.isInstance(e)) {
				return true;
			}
		}
		return false;
	}
}
