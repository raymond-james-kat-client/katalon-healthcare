import com.kms.katalon.util.CryptoUtil

import katalon.android.common.SignInPage
import katalon.fw.lib.Page

Page.nav(SignInPage)
	.setEmail(invalidEmail)
	.setPassword(CryptoUtil.encode(CryptoUtil.getDefault(invalidPassword)))
	.tapSignIn()
	.verifyErrorMessage(emailErrorMessage)
	.verifyErrorMessage(passwordErrorMessage)