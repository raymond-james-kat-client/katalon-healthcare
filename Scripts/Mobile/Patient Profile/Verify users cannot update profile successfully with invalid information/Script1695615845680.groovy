import com.kms.katalon.core.annotation.SetUp

import katalon.android.common.HomePage
import katalon.android.common.ProfilePage
import katalon.android.common.SignInPage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.withGender("Male")
	.getFirst()

Page.nav(SignInPage)
	.logIn(user.email, user.pwd)
	
'Tap Profile > Edit profile'
Page.nav(HomePage)
	.tapProfilePicture()
	.tapEditProfileButton()
	
'Set invalid phone number'
'Verify'
'- Error message is displayed for each invalid case'
Page.nav(ProfilePage)
	.setPhoneNumber(usedPhoneNumber)
	.tapSaveChanges()
	.verifyCorrectErrorMessage("Phone number is already in use")
	
	.setPhoneNumber("034567891")
	.tapSaveChanges()
	.verifyCorrectErrorMessage("Please enter a valid Vietnam phone number (e.g., 0912345678 or 912345678)")
	
	.setPhoneNumber("9182736450")
	.tapSaveChanges()
	.verifyCorrectErrorMessage("Please enter a valid Vietnam phone number (e.g., 0912345678 or 912345678)")
	
	.setPhoneNumber("034567891a")
	.tapSaveChanges()
	.verifyCorrectErrorMessage("Please enter a valid Vietnam phone number (e.g., 0912345678 or 912345678)")
	
	.setPhoneNumber("~!@#%^&*()-+")
	.tapSaveChanges()
	.verifyCorrectErrorMessage("Please enter a valid Vietnam phone number (e.g., 0912345678 or 912345678)")
	
	.setPhoneNumber("")
	.verifyCorrectErrorMessage("Please enter your phone number")

'Set invalid full name'
'Verify'
'- Error message is displayed'
Page.nav(ProfilePage)
	.setFullName("")
	.verifyCorrectErrorMessage("Please enter your full name")
	
'Set invalid birthday'
'Verify'
'- Error message is displayed for each invalid case'
Page.nav(ProfilePage)
	.setBirthday("")
	.verifyCorrectErrorMessage("Please enter your DOB")
	
	.setBirthday("06/25/2005")
	.verifyCorrectErrorMessage("Please enter a valid date with format: dd-MM-yyyy")
	
	.setBirthday("2013-25-15")
	.verifyCorrectErrorMessage("Please enter a valid date with format: dd-MM-yyyy")
	
	.setBirthday("37-06-2013")
	.verifyCorrectErrorMessage("Please enter a valid date with format: dd-MM-yyyy")
	
@SetUp
def setUp() {
	Credential user = Page.nav(Credential)
		.getCredentials()
		.withRole("Patient")
		.withGender("Female")
		.getFirst()
		
	usedPhoneNumber = user.phoneNumber
}