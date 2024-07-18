import internal.GlobalVariable
import katalon.android.common.SignInPage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.getFirst()

Page.nav(SignInPage)	
	.setEmail(user.email)
	.setPassword('wrongP@ssw0rd')
	.tapSignIn()
	.verifyErrorMessage('Invalid email or password')
	
	.setEmail('wrongemail@gmail.com')
	.setPassword(user.pwd)
	.tapSignIn()
	.verifyErrorMessage('Invalid email or password')