import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown

import groovy.json.JsonOutput
import internal.GlobalVariable
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.DoctorManagementService
import katalon.services.SignInService

String activateDoctorUrl = GlobalVariable.apiUrl +  "doctors/activate/${doctor.id}"

'Deactivate doctor account'
Page.nav(DoctorManagementService)
	.initRequestObject()
	.setUrl(activateDoctorUrl)
	.setJsonContentTypeHeader()
	.setBearerAuthorizationHeader(adminAccessToken)
	.sendPutRequest()
	.verifyStatusCode(200)
	.verifyJSONResponseBodyExactEqual("""
{
  "data": {
    "id": "${doctor.id}",
    "fullName": "${doctor.fullName}",
    "email": "${doctor.email}",
    "phoneNumber": "${doctor.phoneNumber}",
	"address" : "${doctor.address}",
	"degree" : "${doctor.degree.toUpperCase().replace(" ", "_")}",
    "description" : "${doctor.description}",
    "gender" : "${doctor.gender.toUpperCase()}",
    "birthday" : "${doctor.birthday}",
	"speciality" : ${JsonOutput.toJson(doctor.speciality)},
    "activated": true
  },
  "message": "Account activated successfully"
}
""")

@SetUp
def setUp() {
	'Get account to log in'
	Credential user = Page.nav(Credential)
		.getCredentials()
		.withRole("Admin")
		.getFirst()
	
	'Log in as admin'
	adminAccessToken = Page.nav(SignInService)
								  .initRequestObject()
								  .signIn(user.email, user.pwd)
								  .getAccessToken()
								  
	'Get doctor information'
	user = Page.nav(Credential)
		.getCredentials()
		.withRole("Doctor")
		.getFirst()
	
	doctor = Page.nav(DoctorManagementService)
		.initRequestObject()
		.getDoctorByName(adminAccessToken, user.fullName)[0]
	
	'Deactivate doctor account'
	Page.nav(DoctorManagementService)
		.initRequestObject()
		.deactivateDoctor(adminAccessToken, doctor.id)
}

@TearDown
def tearDown() {
	'Activate doctor account'
	Page.nav(DoctorManagementService)
		.initRequestObject()
		.activateDoctor(adminAccessToken, doctor.id)
}