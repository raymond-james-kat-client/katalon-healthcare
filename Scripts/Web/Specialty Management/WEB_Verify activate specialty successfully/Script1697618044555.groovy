import com.kms.katalon.core.annotation.TearDown

import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.SignInService
import katalon.services.SpecialtyManagementService
import katalon.web.admin.LeftNavBar
import katalon.web.admin.Specialty
import katalon.web.common.SignInPage
import katalon.web.common.WelcomePage

'Get account to log in'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

'Deactivate the 1st specialty via api'
token = Page.nav(SignInService).initRequestObject()
						.signIn(user.email, user.pwd)
						.getAccessToken()	
Page.nav(SpecialtyManagementService).initRequestObject()
									.getSpecialtyList(token)
specialtyUUID = Page.nav(SpecialtyManagementService).getSpecialtyId(0)
specialtyStatus = Page.nav(SpecialtyManagementService).getSpecialtyStatus(0)
specialtyName = Page.nav(SpecialtyManagementService).getSpecialtyName(0)
Page.nav(SpecialtyManagementService).initRequestObject()
									.deactivateSpecialty(token, specialtyUUID)	
									
'Sign in as admin and navigate to specialty management'
Page.nav(WelcomePage).clickSignInButton()
Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
Page.nav(LeftNavBar).clickSpecialty()
														
'Verify activate specialty successfully'
Page.nav(Specialty).clickSpecialtyTripleDot(0)
					.activateSpecialty(specialtyName)
					.verifyNotiMessage("Reactivate the speciality successfully")
					.verifySpecialtyStatus(0, "ACTIVATED")
					
@TearDown
def tearDown() {
	'Revert specialty status'
	if (specialtyStatus == "true") {
		Page.nav(SpecialtyManagementService).initRequestObject()
											.activateSpecialty(token, specialtyUUID)
											.verifyStatusCode(200)
	} else {
		Page.nav(SpecialtyManagementService).initRequestObject()
											.deactivateSpecialty(token, specialtyUUID)
											.verifyStatusCode(200)
	}
}

															