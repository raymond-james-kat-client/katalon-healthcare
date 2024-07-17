import internal.GlobalVariable
import katalon.android.common.RandomProfileGenerator
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.DoctorManagementService
import katalon.services.SignInService

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

'Log in as admin'
def adminAccessToken = Page.nav(SignInService)
							  .initRequestObject()
							  .signIn(user.email, user.pwd)
							  .getAccessToken()
	
def email = Page.nav(RandomProfileGenerator).generateRandomEmail()
def name = Page.nav(RandomProfileGenerator).generateRandomName()
def phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def password = "P@ssw0rdAuto"

Page.nav(DoctorManagementService)
	.initRequestObject()
	.createDoctor(adminAccessToken, email, name, phoneNumber, password)
	.verifyPropertyValue("message", "An email requesting a profile update will be sent to ${email} if it exists")
	.verifyPropertyValue("data.email", email)
	.verifyPropertyValue("data.fullName", name)
	.verifyPropertyValue("data.phoneNumber", phoneNumber)
	.verifyPropertyValue("data.activated", false)
	.hasProperty("data.id")
	