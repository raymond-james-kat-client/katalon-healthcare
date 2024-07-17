import katalon.android.common.RandomProfileGenerator
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

String fullName = Page.nav(RandomProfileGenerator).generateRandomName()
String phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
String gender = Page.nav(RandomProfileGenerator).generateRandomGender()
String birthday = Page.nav(RandomProfileGenerator).generateRandomBirthday()
String degree = Page.nav(RandomProfileGenerator).generateRandomDegree()
String speciality = Page.nav(RandomProfileGenerator).generateRandomSpeciality()
String address = Page.nav(RandomProfileGenerator).generateRandomAddress()
String description = Page.nav(RandomProfileGenerator).generateRandomDescription()

'Login as admin'
Page.nav(WelcomePage).clickSignInButton()

Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
Page.nav(WelcomePage).verifySignInSuccessfully(user.fullName)

'Click doctor button on left side bar'
Page.nav(LeftNavBar).clickDoctor()

'Get random row and save doctor information of that row'
String row = Page.nav(Doctor).getDoctorInformationOfRandomRow()

'Compare information of view list with information of doctor profile'
Page.nav(Doctor).clickMoreIcon(row)
				.clickEditDoctorProfile()
				.verifyEditDoctorProfileWithDoctorViewList()

'Update doctor profile'
Page.nav(Doctor).uploadAvatar()
				.inputFullName(fullName)
				.inputPhoneNumber(phoneNumber)
				.inputBirthday(birthday)
				.selectGender(gender)
				.selectDegree(degree)
				.selectSpeciality(speciality)
				.inputAddress(address)
				.inputDescription(description)
				.getEditDoctorProfile()
				.clickConfirm()
				.verifyNotiMessage("Update profile successfully")

'Compare doctor information after updating with information after updating'
Page.nav(Doctor).clickMoreIcon(row)
				.clickEditDoctorProfile()
				.verifyAllDoctorProfile()
				.clickCancel()

'Compare information of view list with information of doctor profile'
Page.nav(Doctor).getDoctorInformationOfRow(row)
				.clickMoreIcon(row)
				.clickEditDoctorProfile()
				.verifyEditDoctorProfileWithDoctorViewList()