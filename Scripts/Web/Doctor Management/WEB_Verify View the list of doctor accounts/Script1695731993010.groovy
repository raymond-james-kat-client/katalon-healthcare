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

'Sign in as admin'
Page.nav(WelcomePage).clickSignInButton()

Page.nav(SignInPage).enterCredentials(user.email, user.pwd)
					.clickSignInButton()
Page.nav(WelcomePage).verifySignInSuccessfully(user.fullName)

Page.nav(LeftNavBar).clickDoctor()

'Verify pagination'
Page.nav(Doctor).verifyTotalElementPerPage()
				.verifyBeforePaginationNotClickable()
				.verifyNextPaginationClickable()
				.setPreviousList()
				.clickNextIcon()
				.verifyBeforePaginationClickable()
				.verifyCurrentListNotEqualToPreviousList()
				.clickLastPagination()
				.verifyNextPaginationNotClickable()

'Sort asc by full name'
Page.nav(Doctor).clickFirstPagination()
				.clickFullNameColumnTitle()
				.setPreviousList()
				.clickLastPagination()
				.verifyFirstElementNameEqualToLastElementName()

'Sort desc by full name'
Page.nav(Doctor).clickFirstPagination()
				.clickFullNameColumnTitle()
				.setPreviousList()
				.clickLastPagination()
				.verifyFirstElementNameEqualToLastElementName()