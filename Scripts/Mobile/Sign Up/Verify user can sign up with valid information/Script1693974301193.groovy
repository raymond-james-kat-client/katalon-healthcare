import katalon.android.common.RandomProfileGenerator
import katalon.android.common.SignInPage
import katalon.android.common.SignUpPage
import katalon.fw.lib.Page

def email = Page.nav(RandomProfileGenerator).generateRandomEmail()
def name = Page.nav(RandomProfileGenerator).generateRandomName()
def phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def password = "P@ssw0rdAuto"

'User tap sign up link'
Page.nav(SignInPage).tapSignUpLink()

'User enter valid information'
Page.nav(SignUpPage).setEmail(email)
					.setFullName(name)
					.setCellPhoneNumber(phoneNumber)
					.setPassword(password)
					.tapTermsAndConditions()
					.swipeScrollBar()
					.tapGoBackToSignUp()
					.checkTermsAndConditions()
					.tapSignUpButton()