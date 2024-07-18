package katalon.android.common

import com.kms.katalon.core.testobject.TestObject

import katalon.fw.lib.BasePageMobile

public class SignInPage extends BasePageMobile<SignInPage> {
	public SignInPage logIn(String email, String password) {
		tap(xpath('//android.view.View[@content-desc="Email\nPassword"]/android.widget.EditText[1]'))
		setText(xpath('//android.view.View[@content-desc="Email\nPassword"]/android.widget.EditText[1]'), email)
		hideKeyboard()

		tap(xpath('//android.view.View[@content-desc="Email\nPassword"]/android.widget.EditText[2]'))
		setText(xpath('//android.view.View[@content-desc="Email\nPassword"]/android.widget.EditText[2]'), password)
		hideKeyboard()

		tap(accessibilityid('Sign In'))

		return this
	}

	public SignInPage setEmail(String email) {
		tap(xpath('//android.view.View[@content-desc="Email\nPassword"]/android.widget.EditText[1]'))
		setText(xpath('//android.view.View[@content-desc="Email\nPassword"]/android.widget.EditText[1]'), email)
		hideKeyboard()
		return this
	}

	public SignInPage setPassword(String password) {
		tap(xpath('//android.view.View[@content-desc="Email\nPassword"]/android.widget.EditText[2]'))
		setEncryptedText(xpath('//android.view.View[@content-desc="Email\nPassword"]/android.widget.EditText[2]'), password)
		hideKeyboard()
		return this
	}

	public SignInPage tapSignIn() {
		tap(accessibilityid('Sign In'))
		return this
	}

	public SignInPage verifyErrorMessage(String message) {
		verifyElementVisible(accessibilityid(message))
		return this
	}


	def void tapSignUpLink() {
		TestObject to = accessibilityid("Sign up")
		tap(to)
	}
}
