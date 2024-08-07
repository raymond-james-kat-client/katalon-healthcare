import katalon.android.common.HomePage
import katalon.android.common.SignInPage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.PatientProfileService
import katalon.services.SignInService

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.getFirst()

Page.nav(SignInPage)
	.setEmail(user.email)
	.setPassword(user.pwd)
	.tapSignIn()
	
"Get user's name to verify correct home page"
String token = Page.nav(SignInService)
				.initRequestObject()
				.signIn(user.email, user.pwd)
				.getAccessToken()
				
String firstName = Page.nav(PatientProfileService)
				.initRequestObject()
				.getPatientProfile(token)
				.parseResponseBodyToJsonObject()
				.data.fullName.split(" ")[0]
	
Page.nav(HomePage).verifySignInSuccessfully(firstName)

'Sign back out'
Page.nav(HomePage)
	.tapProfilePicture()
	.tapSignOut()