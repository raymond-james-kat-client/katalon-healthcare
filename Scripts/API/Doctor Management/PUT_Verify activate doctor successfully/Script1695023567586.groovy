import internal.GlobalVariable
import katalon.android.common.RandomProfileGenerator
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.DoctorManagementService
import katalon.services.SignInService

'Get account to log in'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

'Log in as admin'
def adminAccessToken = Page.nav(SignInService)
							  .initRequestObject()
							  .signIn(user.email, user.pwd)
							  .getAccessToken()

'Create doctor account and get id'
def email = Page.nav(RandomProfileGenerator).generateRandomEmail()
def name = Page.nav(RandomProfileGenerator).generateRandomName()
def phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def password = "P@ssw0rdAuto"

def doctorId = Page.nav(DoctorManagementService)
				   .initRequestObject()
				   .createDoctor(adminAccessToken, email, name, phoneNumber, password)
				   .parseResponseBodyToJsonObject()
				   .data.id

'Activate doctor account'
Page.nav(DoctorManagementService)
	.initRequestObject()
	.activateDoctor(adminAccessToken, doctorId)
	
	
	