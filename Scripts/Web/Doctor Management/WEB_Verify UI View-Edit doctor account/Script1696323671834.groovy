import internal.GlobalVariable
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.web.admin.Doctor
import katalon.web.admin.LeftNavBar
import katalon.web.common.SignInPage
import katalon.web.common.WelcomePage

'Get account to log in'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

Page.nav(WelcomePage).clickSignInButton()

Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
Page.nav(WelcomePage).verifySignInSuccessfully(user.fullName)

Page.nav(LeftNavBar).clickDoctor()

String row = Page.nav(Doctor).clickRandomMoreIcon()

Page.nav(Doctor).clickEditDoctorProfile()
				.verifyEditDoctorTitle()
				.verifyUploadPhotoClickable()
				.verifyPhotoFieldRequired()
				.verifyFullNameFieldRequired()
				.verifyPhoneNumberFieldRequired()
				.verifyGenderFieldRequired()
				.verifyGenderClickable("Male")
				.verifyGenderClickable("Female")
				.verifyCalendarIconClickable()
				.verifyDegreeFieldRequired()
				.verifySpecialityFieldRequired()
				.verifyDescriptionFieldRequired()
				.verifyCloseIconClickable()
				.verifyConfirmClickable()