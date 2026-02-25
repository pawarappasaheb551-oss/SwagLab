package listener;

	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;
	import org.testng.ITestListener;
	import org.testng.ITestResult;

	public class TestListener implements ITestListener {
	    private static final Logger logger = LogManager.getLogger(TestListener.class);

	    @Override
	    public void onTestStart(ITestResult result) {
	        logger.info("Starting Test: " + result.getName());
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	        logger.info("Test Passed: " + result.getName());
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	        logger.error("Test Failed: " + result.getName());
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {
	        logger.warn("Test Skipped: " + result.getName());
	    }
	}

