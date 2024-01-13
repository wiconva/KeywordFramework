package tests.thisFrameworkTest;

import org.testng.annotations.*;
import core.controller.TestController;

public class FrameworkTest extends TestController {
    //This field is read usign java reflexion.
    public final String DATA_FILE_NAME = "FrameworkTestData";

    //this test is ok.
    @Test(groups = {"FrameworkTest","T0000_HappyPathTest"})
    public void T0000_HappyPathTest() {executeTest();}

    //This is ok, the test have many callto.
    @Test(groups = {"FrameworkTest","T0001_HappyPathCallToTest"})
    public void T0001_HappyPathCallToTest() {executeTest();}

    //This test fail because the keyword used not exist.
    @Test(groups = {"FrameworkTest","T0002_KeywordDontExistErrorTest"})
    public void T0002_KeywordDontExistErrorTest() {executeTest();}

    //This test fail because the WebObject Name is not declare on WebObject repository file.
    @Test(groups = {"FrameworkTest","T0003_WebObjectNotDefineErrorText"})
    public void T0003_WebObjectNotDefineErrorText () {executeTest();}

    //This test fail because the variables input used is not store in profile properties or execution output.
    @Test(groups = {"FrameworkTest","T0004_VarInputNotDefineErrorTest"})
    public void T0004_VarInputNotDefineErrorTest () {executeTest();}

    //this test fail because the page object repository does not exist.
    @Test(groups = {"FrameworkTest", "T0005_PageRepositoryNotDefineErrorTest"})
    public void T0005_PageRepositoryNotDefineErrorTest() {executeTest();}

    //This test fail because not exist one test file excell whit the name of test function.
    @Test(groups = {"FrameworkTest","T0006_TestFileNotExistErrorTest"})
    public void T0006_TestFileNotExistErrorTest() {executeTest();}

    //This test fail because exist more that one file with the seem name.
    @Test(groups = {"FrameworkTest","T0007_MoreThanOneFileTestErrorTest"})
    public void T0007_MoreThanOneFileTestErrorTest() {executeTest();}

    //Test ok, for data test file.
    @Test(groups = {"FrameworkTest","T0008_HappyPathDataFileTest"})
    public void T0008_HappyPathDataFileTest() {executeTest();}

    //Test ok, for data test file.
    @Test(groups = {"FrameworkTest","T0009_VarInputNotDefineInDataFileErrorTest"})
    public void T0009_VarInputNotDefineInDataFileErrorTest() {executeTest();}

    //This test fail because cant execute the keword, attemps test.
    @Test(groups = {"FrameworkTest","T0010_NotExecutionActionWithAttempsError"})
    public void T0010_NotExecutionActionWithAttempsError() {executeTest();}

}
