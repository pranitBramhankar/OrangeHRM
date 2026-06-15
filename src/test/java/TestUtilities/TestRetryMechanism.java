package TestUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;
import java.util.logging.Level;

public class TestRetryMechanism {
	private WebDriver driver;
	
	public TestRetryMechanism(WebDriver driver) {
		this.driver = driver;
	}
	
	private static final Logger logger =  LogManager.getLogger(TestRetryMechanism.class);
	
	@SafeVarargs
	public static <T> T retryOnException(Supplier<T> operation, int maxAttempts, Class<? extends Throwable>...retryOn) {
		int attempts = 0;
		while(true) {
			try {
				T op = operation.get();
				 logger.info("Operation succeeded on attempt " + (attempts + 1));
				return op;	
			}catch(Throwable e){
				attempts++;
				
				boolean shouldRetry = isInstanceOfAny(e, retryOn);
				
				if(!shouldRetry || attempts>=maxAttempts) {
					 logger.error("Failed to click element: {}",e);
					if(e instanceof RuntimeException) {
						throw (RuntimeException) e;
					}
					
					throw new RuntimeException(e);
				}
				
				logger.info("Retry attempt " + attempts + " due to: " + e.getClass().getSimpleName(), e);
				
			}
		}	
		
	
	}
	public static boolean isInstanceOfAny(Throwable e, Class<? extends Throwable> [] passedExceptions) {
		
		if(passedExceptions == null || passedExceptions.length==0) {
			return false;
		}
		
		for(Class<? extends Throwable> exception : passedExceptions) {
			if(exception.isInstance(e)) {
				return true;
			}
		}
		return false;
	}
	
	@SafeVarargs
	public static void retryOnException(Runnable operation, int maxAttempts, Class<? extends Throwable>...retryOn) {
		int attempts = 0;
		while(true) {
			attempts++;
			
			try {
				operation.run();
				 logger.info("Operation succeeded on attempt " + attempts);
				return;
			}catch(Throwable e) {
				
				boolean shouldRetry = isInstanceOfAny(e,retryOn);
				if(!shouldRetry || attempts>=maxAttempts) {
					 logger.error("Failed to click element: {}",e);
				    if(e instanceof RuntimeException) {
				        throw (RuntimeException)e;
				    }
				    
				    throw new RuntimeException(e);
				}
				
				logger.info("Retry attempt " + attempts + " due to: " + e.getClass().getSimpleName(), e);
			}	
		}
	}


}
