import internal.GlobalVariable
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.web.common.HeaderBar
import katalon.web.common.SignInPage
import katalon.web.common.WelcomePage
import katalon.web.profile.PatientProfile

'Get account to log in'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.getFirst()

Page.nav(WelcomePage).clickSignInButton()
Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
					
'Navigate to patient profile'
Page.nav(HeaderBar).clickAvatar()
					.clickProfile()

'Get information before updating'
String patientFullName = Page.nav(PatientProfile).getFullName()
String patientPhoneNumber = Page.nav(PatientProfile).getPhoneNumber()
String patientBirthday = Page.nav(PatientProfile).getBirthday()
String patientGender = Page.nav(PatientProfile).getGender()
String newGender
if (patientGender == "MALE") {
	newGender = "FEMALE"
} else newGender = "MALE"

String patientHeight = Page.nav(PatientProfile).getHeight()
String patientWeight = Page.nav(PatientProfile).getWeight()
String patientAddress = Page.nav(PatientProfile).getAddress()

'Input new information then click RESET button'
Page.nav(PatientProfile).inputFullName("new full name")
						.inputPhoneNumber("new phone number")
						.inputBirthday("14093000")
						.clickGenderDropdown()
						.selectGender(newGender)
						.inputHeight("new height")
						.inputWeight("new weight")
						.inputAddress("new address")
						.clickResetButton()
						.verifyFullName(patientFullName)
						.verifyPhoneNumber(patientPhoneNumber)
						.verifyBirthday(patientBirthday)
						.verifyGender(patientGender)
						.verifyHeight(patientHeight)
						.verifyWeight(patientWeight)
						.verifyAddress(patientAddress)
						
						