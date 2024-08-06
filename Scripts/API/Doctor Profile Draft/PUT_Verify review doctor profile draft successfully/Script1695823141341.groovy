import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown

import internal.GlobalVariable
import katalon.android.common.RandomProfileGenerator
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.DoctorManagementService
import katalon.services.DoctorProfileService
import katalon.services.DraftChangesService
import katalon.services.SignInService
import katalon.services.SpecialtyManagementService

'Get admin information'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()
	
adminAccessToken = Page.nav(SignInService)
	.initRequestObject()
	.signIn(user.email, user.pwd)
	.getAccessToken()

'Generate random information to create doctor'
def email = Page.nav(RandomProfileGenerator).generateRandomEmail()
def phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def password = "P@ssw0rd@auto"
	
'Review (Approve or Reject)'
def reviewProfileUrl = GlobalVariable.apiUrl + "doctors/review-profile/${doctorId}"

Page.nav(DraftChangesService)
	.initRequestObject()
	.setUrl(reviewProfileUrl)
	.setJsonContentTypeHeader()
	.setBearerAuthorizationHeader(adminAccessToken)
	.setPayLoad('{"status": "'+review+'"}')
	.sendPutRequest()
	.verifyStatusCode(200)
	.verifyPropertyValue("message", "Review sent successfully")
	
@SetUp
def setUp() {
	'Get doctor information'
	Credential user = Page.nav(Credential)
		.getCredentials()
		.withRole("Doctor")
		.getFirst()
	
	'Log in as doctor to request change profile'
	doctorToken = Page.nav(SignInService)
		.initRequestObject()
		.signIn(user.email, user.pwd)
		.getAccessToken()
		
	'Generate random information to update doctor profile'
	def newPhoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
	def gender = Page.nav(RandomProfileGenerator).generateRandomGender()
	def address = Page.nav(RandomProfileGenerator).generateRandomAddress()
	def birthday = Page.nav(RandomProfileGenerator).generateRandomBirthday()
	def speciality = Page.nav(RandomProfileGenerator).generateRandomSpeciality()
	def degree = Page.nav(RandomProfileGenerator).generateRandomDegree()
	def description = Page.nav(RandomProfileGenerator).generateRandomDescription()
	def photo = "photoAuto"
	
	'Doctor request change profile and get doctor id'
	doctorId = Page.nav(DoctorProfileService)
		.initRequestObject()
		.updateDoctorProfile(doctorToken, user.fullName, newPhoneNumber, gender, birthday, address, speciality, degree, description)
		.parseResponseBodyToJsonObject()
		.data.id
}

@TearDown
def tearDown() {
	'Get account to log in'
	Credential user = Page.nav(Credential)
		.getCredentials()
		.withRole("Doctor")
		.getFirst()
	
	specialityId = Page.nav(SpecialtyManagementService).getSpecialtyByName(adminAccessToken, user.speciality).id
		
	Page.nav(DoctorManagementService)
	.initRequestObject()
	.updateDoctorProfile(doctorId, user.fullName, user.phoneNumber, user.gender, user.birthday, user.address, specialityId, user.degree, user.description, adminAccessToken)
}