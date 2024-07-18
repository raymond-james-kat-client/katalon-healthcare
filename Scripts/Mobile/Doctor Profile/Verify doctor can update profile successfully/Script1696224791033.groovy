import com.kms.katalon.core.annotation.TearDown

import katalon.android.common.HomePage
import katalon.android.common.RandomProfileGenerator
import katalon.android.common.SignInPage
import katalon.android.doctor.DoctorProfilePage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.DoctorManagementService
import katalon.services.SignInService

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Doctor")
	.getFirst()

'Log in as doctor'
Page.nav(SignInPage)
	.logIn(user.email, user.pwd)

'Update doctor profile'
Page.nav(HomePage)
	.tapProfilePicture()
	.tapEditProfileButton()
	
name = Page.nav(RandomProfileGenerator).generateRandomName()
def birthday =	Page.nav(RandomProfileGenerator).generateRandomBirthday()
def gender = Page.nav(RandomProfileGenerator).generateRandomGender()
def address = Page.nav(RandomProfileGenerator).generateRandomAddress()
phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def degree = Page.nav(RandomProfileGenerator).generateRandomDegree()
def description = Page.nav(RandomProfileGenerator).generateRandomDescription()

Page.nav(DoctorProfilePage)
	.setFullName(name)
	.chooseGender(gender)
	.swipeToBottom()
	.setPhoneNumber(phoneNumber)
	.chooseSpeciality()
	.chooseDegree()
	.setAddress(address)
	.setBirthday(birthday)
	.setDescription(description)
	.tapSaveChanges()
	.verifyRequestSent()
	.goBackToHomePage()

Page.nav(HomePage)
	.tapProfilePicture()
	.tapSignOut()
	
@TearDown
def tearDown() {
	'Get account to log in'
	Credential doctor = Page.nav(Credential)
		.getCredentials()
		.withRole("Doctor")
		.getFirst()
		
	Credential admin = Page.nav(Credential)
		.getCredentials()
		.withRole("Doctor")
		.getFirst()
		
	String adminAccessToken = Page.nav(SignInService)
		.initRequestObject()
		.signIn(admin.email, admin.pwd)
		.getAccessToken()
	
	Page.nav(DoctorManagementService)
	.initRequestObject()
	.updateDoctorProfile(doctorUuid, doctor.fullName, doctor.phoneNumber, doctor.gender, doctor.birthday, doctor.address, doctor.speciality, doctor.degree, "", adminAccessToken)
}