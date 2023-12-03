package tests.thisFrameworkTest;

import org.testng.annotations.*;
import utilities.framework.TestRunner;

public class FrameworkTest extends TestRunner {
    public final String DATA_FILE_NAME = "LoginTestData";

    //this test is ok.
    @Test(groups = {"Framework","Logintest_0000"})
    public void Logintest_0000() {runTest("Logintest_0000");}

    //This test fail because the keyword used not exist.
    @Test(groups = {"Framework","LoginPage","LogintestError_0001"})
    public void LogintestError_0001 () {runTest("LogintestError_0001");}

    //This test fail because the WebObject Name is not declare on WebObject repository file.
    @Test(groups = {"Framework","LoginPage","LogintestError_0002"})
    public void LogintestError_0002 () {runTest("LogintestError_0002");}

    //This test fail because the variables input used is not store in profile properties or execution output.
    @Test(groups = {"Framework","LoginPage","LogintestError_0003"})
    public void LogintestError_0003 () {runTest("LogintestError_0003");}

    //this test fail because the page object repository does not exist.
    @Test(groups = {"Framework","LoginPage","LogintestError_0004"})
    public void LogintestError_0004() {runTest("LogintestError_0004");}

    //This test fail because the arguments of the runTest function not match with the name of test function.
    @Test(groups = {"Framework","LoginPage","LogintestError_0005"})
    public void LogintestError_0005() {runTest("LogintestError_0005");}

    //This test fail because not exist one test file excell whit the name of test function.
    @Test(groups = {"Framework","LoginPage","LogintestError_0006"})
    public void LogintestError_00061() {runTest("LogintestError_0006");}

    //This test fail because exist more that one file with the seem name.
    @Test(groups = {"Framework","LoginPage","LogintestError_0007"})
    public void LogintestError_0007() {runTest("LogintestError_0007");}

    //This test have many callto.
    @Test(groups = {"Framework","LoginPage","CallToTest_0000"})
    public void CallToTest_0000() {runTest("CallToTest_0000");}
}
