package demo.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersClass implements ITestListener{
    public void onTestStart(ITestResult result) {
        System.out.println("=======================================StartTestCase:" +result.getName()+"================================================");
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("=======================================EndTestCase:" +result.getName()+"================================================");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("=======================================FailedTestCase:" +result.getName()+"================================================");
    }
}
