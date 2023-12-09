package tests.thisFrameworkTest;

import org.testng.annotations.*;
import core.controller.TestController;

public class FrameworkTest extends TestController {
    //This field is read usign java reflexion.
    public final String DATA_FILE_NAME = "FrameworkTestData";

    //this test is ok.
    @Test(groups = {"Framework","Logintest_0000"})
    public void Logintest_0000() {executeTest();}

    //This test fail because the keyword used not exist.
    @Test(groups = {"Framework","LoginPage","LogintestError_0001"})
    public void LogintestError_0001 () {executeTest();}

    //This test fail because the WebObject Name is not declare on WebObject repository file.
    @Test(groups = {"Framework","LoginPage","LogintestError_0002"})
    public void LogintestError_0002 () {executeTest();}

    //This test fail because the variables input used is not store in profile properties or execution output.
    @Test(groups = {"Framework","LoginPage","LogintestError_0003"})
    public void LogintestError_0003 () {executeTest();}

    //this test fail because the page object repository does not exist.
    @Test(groups = {"Framework","LoginPage","LogintestError_0004"})
    public void LogintestError_0004() {executeTest();}

    //This test fail because not exist one test file excell whit the name of test function.
    @Test(groups = {"Framework","LoginPage","LogintestError_0006"})
    public void LogintestError_0006() {executeTest();}

    //This test fail because exist more that one file with the seem name.
    @Test(groups = {"Framework","LoginPage","LogintestError_0007"})
    public void LogintestError_0007() {executeTest();}

    //This is ok, the test have many callto.
    @Test(groups = {"Framework","LoginPage","CallToTest_0000"})
    public void CallToTest_0000() {executeTest();}

}
