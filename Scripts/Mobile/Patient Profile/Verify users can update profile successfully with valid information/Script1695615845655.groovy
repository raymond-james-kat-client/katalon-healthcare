import com.kms.katalon.core.annotation.TearDown

import katalon.android.common.HomePage
import katalon.android.common.ProfilePage
import katalon.android.common.RandomProfileGenerator
import katalon.android.common.SignInPage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.PatientManagementService
import katalon.services.SignInService

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.getFirst()

Page.nav(SignInPage)
	.logIn(user.email, user.pwd)

Page.nav(HomePage)
	.tapProfilePicture()
	.tapEditProfileButton()
	
def fullName = Page.nav(RandomProfileGenerator).generateRandomName()
def birthday = Page.nav(RandomProfileGenerator).generateRandomBirthday()
def gender = Page.nav(RandomProfileGenerator).generateRandomGender()
def address = Page.nav(RandomProfileGenerator).generateRandomAddress()
phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def weight = Page.nav(RandomProfileGenerator).generateRandomWeight()
def height = Page.nav(RandomProfileGenerator).generateRandomHeight()
	
Page.nav(ProfilePage)
	.setFullName(fullName)
	.chooseGender(gender.toLowerCase().capitalize())
	.setPhoneNumber(phoneNumber)
	.setWeight(weight)
	.setHeight(height)
	.setBirthday(birthday)
	.tapSaveChanges()
	.verifyUpdateSuccessfully()
	.goBackToHomePage()
	
Page.nav(HomePage)
	.tapProfilePicture()
	.tapEditProfileButton()
	
Page.nav(ProfilePage)
	.verifyCorrectFullName(fullName)
	.verifyCorrectGender(gender.toLowerCase().capitalize())
	.verifyCorrectPhoneNumber(phoneNumber)
	.verifyCorrectWeight(weight)
	.verifyCorrectHeight(height)
	.verifyCorrectBirthday(birthday.toString())
	.tapDecrease1kg()
	.tapIncrease1kg()
	.tapIncrease1kg()
	.verifyCorrectWeight((weight.toInteger() + 1).toString())
	.tapDecrease1cm()
	.tapDecrease1cm()
	.tapIncrease1cm()
	.verifyCorrectHeight((height.toInteger() - 1).toString())
	.goBackToHomePage()

Page.nav(HomePage)
	.tapProfilePicture()
	.tapSignOut()
	
@TearDown
def tearDown() {
	'Get account'
	Credential user = Page.nav(Credential)
		.getCredentials()
		.withRole("Patient")
		.getFirst()
		
	String accessToken = Page.nav(SignInService)
		.initRequestObject()
		.signIn(user.email, user.pwd)
		.getAccessToken()
	
	Page.nav(PatientManagementService)
		.initRequestObject()
		.updatePatient(accessToken, user.fullName, user.address, user.birthday, user.phoneNumber, user.gender, user.weight, user.height)
}