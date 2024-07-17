import internal.GlobalVariable
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.web.common.SignInPage
import katalon.web.common.WelcomePage

'Get account for login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.getFirst()

'Navigate to Sign In page'
Page.nav(WelcomePage).clickSignInButton()

'Enter credentials'
Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
					
'Verify sign in successfully'
Page.nav(WelcomePage).verifySignInSuccessfully(user.fullName)