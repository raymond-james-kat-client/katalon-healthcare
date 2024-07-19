import internal.GlobalVariable
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

'Update doctor profile'
Page.nav(HomePage)
	.tapProfilePicture()
	.tapEditProfileButton()

'Verify invalid information in fields'
Page.nav(DoctorProfilePage)
	.setFullName(invalidFullName)
	.verifyCorrectErrorMessage("Please enter alphabetic characters only (a-z, A-Z). Numbers, symbols, and special characters are not allowed")
	.swipeToBottom()
	.setPhoneNumber(invalidPhoneNumber)
	.verifyCorrectErrorMessage("Please enter a valid Vietnam phone number (e.g., 0912345678 or 912345678)")
	.setBirthday(invalidBirthday)
	.verifyCorrectErrorMessage("Please enter a valid date with format: yyyy-MM-dd")
	.goBackToHomePage()

Page.nav(HomePage)
	.tapProfilePicture()
	.tapSignOut()