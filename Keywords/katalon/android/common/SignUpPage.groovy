package katalon.android.common

import com.kms.katalon.core.testobject.TestObject

import katalon.fw.lib.BasePageMobile

public class SignUpPage extends BasePageMobile<SignUpPage> {
	def SignUpPage setEmail(String value) {
		TestObject to = xpath("//android.view.View[@content-desc='Email\nFull name\nCell phone number\nPassword\nI agree with \nKindly accept the necessary terms & conditions']/android.widget.EditText[1]")
		tap(to)
		setText(to, value)
		hideKeyboard()
		return this
	}

	def SignUpPage setFullName(String value) {
		TestObject to = xpath("//android.view.View[@content-desc='Email\nFull name\nCell phone number\nPassword\nI agree with \nKindly accept the necessary terms & conditions']/android.widget.EditText[2]")
		tap(to)
		setText(to, value)
		hideKeyboard()
		return this
	}

	def SignUpPage setCellPhoneNumber(String value) {
		TestObject to = xpath("//android.view.View[@content-desc='Email\nFull name\nCell phone number\nPassword\nI agree with \nKindly accept the necessary terms & conditions']/android.widget.EditText[3]")
		tap(to)
		setText(to, value)
		hideKeyboard()
		return this
	}

	def SignUpPage setPassword(String value) {
		TestObject to = xpath("//android.view.View[@content-desc='Email\nFull name\nCell phone number\nPassword\nI agree with \nKindly accept the necessary terms & conditions']/android.widget.EditText[4]")
		tap(to)
		setText(to, value)
		hideKeyboard()
		return this
	}

	def SignUpPage tapTermsAndConditions() {
		TestObject to = accessibilityid("Terms & Conditions")
		tap(to)
		return this
	}

	def SignUpPage tapSignUpButton() {
		TestObject to = accessibilityid("Sign Up")
		tap(to)
		return this
	}

	def SignUpPage tapGoBackToSignUp() {
		TestObject to = accessibilityid("Go back to Sign Up")
		tap(to)
		return this
	}

	def SignUpPage checkTermsAndConditions() {
		TestObject to = xpath("","","android.widget.CheckBox")
		tap(to)
		return this
	}

	def SignUpPage swipeScrollBar() {
		swipe(1291, 533, 1291, 2453)
		return this
	}

	def SignUpPage verifyErrorMessage(String value) {
		TestObject to = accessibilityid(value)
		verifyElementExist(to)
		return this
	}
}
