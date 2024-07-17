package katalon.web.common

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import katalon.fw.lib.BasePageWeb

public class SignInPage extends BasePageWeb<SignInPage> {
	TestObject signInBtn = btn("Sign in")

	public SignInPage enterCredentials(String email, String password) {
		WebUI.sendKeys(id("emailInput"), email)
		WebUI.setEncryptedText(id("passwordInput"), password)
		return this
	}

	public SignInPage clickSignInButton() {
		WebUI.click(signInBtn)
		return this
	}
}
