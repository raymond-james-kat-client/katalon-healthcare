import com.kms.katalon.core.annotation.SetUp

import katalon.android.common.RandomProfileGenerator
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.DoctorManagementService
import katalon.services.DoctorProfileService
import katalon.services.SignInService
	
'Get account to log in'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Doctor")
	.getFirst()

'Log in as doctor'
def doctorAccessToken = Page.nav(SignInService)
							.initRequestObject()
							.signIn(user.email, user.pwd)
							.getAccessToken()

def fullName = Page.nav(RandomProfileGenerator).generateRandomName()
def birthday = Page.nav(RandomProfileGenerator).generateRandomBirthday()
def gender = Page.nav(RandomProfileGenerator).generateRandomGender()
def address = Page.nav(RandomProfileGenerator).generateRandomAddress()
def phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def degree = Page.nav(RandomProfileGenerator).generateRandomDegree()
def speciality = Page.nav(RandomProfileGenerator).generateRandomSpeciality()
def description = Page.nav(RandomProfileGenerator).generateRandomDescription()
	
Page.nav(DoctorProfileService)
	.initRequestObject()
	.updateDoctorProfile(doctorAccessToken, fullName, phoneNumber, gender, birthday, address, speciality, degree, description)
	.verifyStatusCode(200)
	.verifyPropertyValue("message", "A request for profile review is sent to the administrator")
	.verifyPropertyValue("data.fullName", fullName)
	.verifyPropertyValue("data.phoneNumber", phoneNumber)
	.verifyPropertyValue("data.gender", gender)
	.verifyPropertyValue("data.birthday", birthday)
	.verifyPropertyValue("data.address", address)
	.verifyPropertyValue("data.speciality.id", speciality)
	.verifyPropertyValue("data.degree", degree)
	.hasProperty("data.id")
	
	
	
	