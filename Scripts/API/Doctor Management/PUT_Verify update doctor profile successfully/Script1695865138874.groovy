import com.kms.katalon.core.annotation.TearDown

import katalon.android.common.RandomProfileGenerator
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.DoctorManagementService
import katalon.services.SignInService
import katalon.services.SpecialtyManagementService
	
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
	
'Generate random information to create doctor'
def email = Page.nav(RandomProfileGenerator).generateRandomEmail()
def fullName = Page.nav(RandomProfileGenerator).generateRandomName()
def phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def password = "P@ssw0rd@auto"
	
'Get doctor uuid'
user = Page.nav(Credential)
	.getCredentials()
	.withRole("Doctor")
	.getFirst()

doctorUuid = Page.nav(DoctorManagementService)
					.initRequestObject()
					.getDoctorByName(adminAccessToken, user.fullName)[0].id

'Generate random information'
fullName = Page.nav(RandomProfileGenerator).generateRandomName()
def birthday = Page.nav(RandomProfileGenerator).generateRandomBirthday()
def gender = Page.nav(RandomProfileGenerator).generateRandomGender()
def address = Page.nav(RandomProfileGenerator).generateRandomAddress()
phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def degree = Page.nav(RandomProfileGenerator).generateRandomDegree()
def speciality = Page.nav(RandomProfileGenerator).generateRandomSpeciality()
def description = Page.nav(RandomProfileGenerator).generateRandomDescription()
	
Page.nav(DoctorManagementService)
	.initRequestObject()
	.updateDoctorProfile(doctorUuid, fullName, phoneNumber, gender, birthday, address, speciality, degree, description, adminAccessToken)
	.verifyStatusCode(200)
	.verifyPropertyValue("message", "Update doctor successfully")
	.verifyPropertyValue("data.phoneNumber", phoneNumber)
	.verifyPropertyValue("data.gender", gender)
	.verifyPropertyValue("data.birthday", birthday)
	.verifyPropertyValue("data.address", address)
	.verifyPropertyValue("data.speciality.id", speciality)
	.verifyPropertyValue("data.degree", degree)
	.hasProperty("data.id")
	
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
	.updateDoctorProfile(doctorUuid, user.fullName, user.phoneNumber, user.gender, user.birthday, user.address, specialityId, user.degree, user.description, adminAccessToken)
}