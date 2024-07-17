import org.apache.commons.lang.RandomStringUtils

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

'Sign in as admin'
Page.nav(WelcomePage).clickSignInButton()
Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
					.waitNotiMessageDisappear()

'Click specialty on left navigation bar'
Page.nav(LeftNavBar).clickSpecialty()
					
'Verify create specialty successfully'
specialityName = "Auto speciality " + RandomStringUtils.randomAlphabetic(5)
Page.nav(Specialty).clickAddNewSpecialty()
					.inputTitle(specialityName)
					.clickConfirm()
					.verifyNotiMessage("Create a new speciality successfully")
					
@TearDown
def tearDown() {
	'Get account to log in'
	Credential user = Page.nav(Credential)
		.getCredentials()
		.withRole("Admin")
		.getFirst()
		
	'Log in as admin'
	def adminAccessToken = Page.nav(SignInService)
								  .initRequestObject()
								  .signIn(user.email, user.pwd)
								  .getAccessToken()
	
	String specialityId = Page.nav(SpecialtyManagementService).initRequestObject().getSpecialtyByName(adminAccessToken, specialityName).id
	
	Page.nav(SpecialtyManagementService).initRequestObject().deactivateSpecialty(adminAccessToken, specialityId)
}