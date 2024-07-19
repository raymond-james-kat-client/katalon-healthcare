import katalon.android.common.HomePage
import katalon.android.common.SignInPage
import katalon.android.doctor.DoctorProfilePage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Doctor")
	.getFirst()

'Log in as doctor'
Page.nav(SignInPage)
	.logIn(user.email, user.pwd)

'Update doctor profile with empty fields'
Page.nav(HomePage)
	.tapProfilePicture()
	.tapEditProfileButton()
	
'Verify correct error message'
Page.nav(DoctorProfilePage)
	.setFullName("")
	.verifyCorrectErrorMessage("Please enter your full name")
	.swipeToBottom()
	.setPhoneNumber("")
	.verifyCorrectErrorMessage("Please enter your phone number")
	.setBirthday("")
	.verifyCorrectErrorMessage("Please enter your DOB")
	.goBackToHomePage()

Page.nav(HomePage)
	.tapProfilePicture()
	.tapSignOut()