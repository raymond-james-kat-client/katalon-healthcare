package katalon.web.common

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import katalon.fw.lib.BasePageWeb

public class WelcomePage extends BasePageWeb<WelcomePage> {
	TestObject signInButton = btn("Sign in")
	def helloUserName = { name -> xpath("//*[text()='Hello, ' and text()='$name']") }

	public WelcomePage clickSignInButton() {
		WebUI.click(signInButton)
		return this
	}

	public WelcomePage verifySignInSuccessfully(String name) {
		WebUI.verifyElementVisible(helloUserName(name.split(" ")[0]))
		return this
	}
}
