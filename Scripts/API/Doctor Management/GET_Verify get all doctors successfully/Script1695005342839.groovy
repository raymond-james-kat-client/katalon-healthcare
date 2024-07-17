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
							  
'Get api response'
def response = Page.nav(DoctorManagementService)
	.initRequestObject()
	.getAllDoctors(adminAccessToken)
	.verifyStatusCode(200)
	.verifyPropertyValue("message", "Get doctors successfully")
	.validateJsonAgainstSchema(schema)