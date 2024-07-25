import internal.GlobalVariable
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

'Sign in as admin and navigate to Specialty management page'
Page.nav(WelcomePage).clickSignInButton()
Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
Page.nav(LeftNavBar).clickSpecialty()

'Get random Specialty in the 1st page of specialty list'
Number randomIndex = Page.nav(Specialty).getRandomSpecialtyIndex()	
String specialtyName = Page.nav(Specialty).getSpecialtyName(randomIndex)
String specialtyDescription = Page.nav(Specialty).getSpecialtyDescription(randomIndex)


'Update specialty'
Page.nav(Specialty).clickSpecialtyTripleDot(randomIndex)
					.clickUpdateButton()
					.verifySpecialtyNameInEditDialog(specialtyName)
					.verifySpecialtyDescriptionInEditDialog(specialtyDescription)
					.inputTitle("This is new title")
					.inputDescription("This is description")
					.clickConfirm()
					.verifyNotiMessage("Update speciality successfully")
					.verifySpecialtyName(randomIndex, "This is new title")
					.verifySpecialtyDescription(randomIndex, "This is description")
					
'Revert specialty information'	
String adminAccessToken = Page.nav(SignInService).initRequestObject()
												.signIn(user.email, user.pwd)
												.getAccessToken()
Number specialtyIndex = Page.nav(SpecialtyManagementService).initRequestObject()
															.getSpecialtyIndexByName(adminAccessToken, "This is new title")
String specialtyUUID = Page.nav(SpecialtyManagementService).getSpecialtyId(specialtyIndex)
Page.nav(SpecialtyManagementService).initRequestObject()
									.updateSpecialty(adminAccessToken, specialtyUUID, specialtyName, specialtyDescription)
									.verifyStatusCode(200)
									
									
									