import katalon.android.common.SignInPage
import katalon.fw.lib.Page

Page.nav(SignInPage)
	.setEmail(invalidEmail)
	.setPassword(invalidPassword)
	.tapSignIn()
	.verifyErrorMessage(emailErrorMessage)
	.verifyErrorMessage(passwordErrorMessage)