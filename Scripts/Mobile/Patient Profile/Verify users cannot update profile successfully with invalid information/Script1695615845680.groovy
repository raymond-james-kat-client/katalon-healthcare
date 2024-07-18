import katalon.android.common.HomePage
import katalon.android.common.ProfilePage
import katalon.android.common.SignInPage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page

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
	
Page.nav(ProfilePage)
	.setPhoneNumber("0903518817")
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

	.setFullName("")
	.verifyCorrectErrorMessage("Please enter your full name")
	
	.setPhoneNumber("")
	.verifyCorrectErrorMessage("Please enter your phone number")

	.setBirthday("")
	.verifyCorrectErrorMessage("Please enter your DOB")
	
	.setBirthday("06/25/2005")
	.verifyCorrectErrorMessage("Please enter a valid date with format: yyyy-MM-dd")
	
	.setBirthday("2013-25-15")
	.verifyCorrectErrorMessage("Please enter a valid date with format: yyyy-MM-dd")
	
	.setBirthday("37-06-2013")
	.verifyCorrectErrorMessage("Please enter a valid date with format: yyyy-MM-dd")
	
	.goBackToHomePage()
	
Page.nav(HomePage)
	.tapProfilePicture()
	.tapSignOut()