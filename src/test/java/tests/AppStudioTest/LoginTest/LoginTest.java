package tests.AppStudioTest.LoginTest;

import core.controller.TestController;
import org.testng.annotations.Test;

public class LoginTest extends TestController {

    //This field is read usign java reflexion.
    public final String DATA_FILE_NAME = "AppStudioTestData";

    @Test(groups = {"LoginPage","T0000","All"})
    public void T0000_VerifyLoginPageTitle() {executeTest();}

    @Test(groups = {"LoginPage","T0001","All"})
    public void T0001_VerifyLoginAreaTitle() {executeTest();}

    @Test(groups = {"LoginPage","T0002","All"})
    public void T0002_VerifyUserInputLabel() {executeTest();}

    @Test(groups = {"LoginPage","T0003","All"})
    public void T0003_VerifyPassInputLabel() {executeTest();}

    @Test(groups = {"LoginPage","T0004","All"})
    public void T0004_VerifyLoginButtonLabel() {executeTest();}
}
