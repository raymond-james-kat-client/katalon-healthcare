import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.PatientProfileService
import katalon.services.SignInService
import katalon.web.common.HeaderBar
import katalon.web.common.SignInPage
import katalon.web.common.WelcomePage
import katalon.web.profile.PatientProfile

'Get account to log in'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.getFirst()

'Sign in as patient'
Page.nav(WelcomePage).clickSignInButton()
Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
					
'Navigate to patient profile'
Page.nav(HeaderBar).clickAvatar()
					.clickProfile()
			
'Verify Update patient profile'
Page.nav(PatientProfile).inputFullName("new full name")
						.inputPhoneNumber("0920202020")
						.inputBirthday("14092001")
						.clickGenderDropdown()
						.selectGender("FEMALE")
						.inputHeight("180")
						.inputWeight("60")
						.inputAddress("This is new addess")
						.clickUpdateProfileButton()
						.verifyNotiMessage("Updated profile successfully")
						
'Revert patient information'						
String patientAccessToken = Page.nav(SignInService).initRequestObject()
							.signIn(user.email, user.pwd)
							.getAccessToken()
							
Page.nav(PatientProfileService).initRequestObject()
								.updatePatientProfile(patientAccessToken, user.fullName, user.address, user.birthday, user.phoneNumber, user.gender.toUpperCase(), user.weight, user.height)
								.verifyStatusCode(200)