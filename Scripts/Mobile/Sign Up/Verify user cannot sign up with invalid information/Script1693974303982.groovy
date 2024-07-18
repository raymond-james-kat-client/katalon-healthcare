import katalon.android.common.SignInPage
import katalon.android.common.SignUpPage
import katalon.fw.lib.Page

'User tap sign up link'
Page.nav(SignInPage).tapSignUpLink()

'User tap sign up button without fulfill information'
Page.nav(SignUpPage).tapSignUpButton()
					
'Verify error message'
Page.nav(SignUpPage).verifyErrorMessage("Please enter your email address")
					.verifyErrorMessage("Please enter your full name")
					.verifyErrorMessage("Please enter your phone number")
					.verifyErrorMessage("Please enter a password")

'User enter invalid information'
Page.nav(SignUpPage).setEmail("user")
					.verifyErrorMessage("Please enter a valid email address (e.g., example@gmail.com)")
					
					.setEmail("user@gmail.com")
					.setFullName("Full Name")
					.setCellPhoneNumber("0")
					.verifyErrorMessage("Please enter a valid Vietnam phone number (e.g., 0123456789 or +84123456789)")
					
					.setCellPhoneNumber("0909123456")
					
					.setPassword('password')
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword('Password')
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword('Password')
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword('PASSWORD')
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword('Passw0rd')
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword('P@ss')
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword('11111111')
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword('@@@@@@@@')
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.checkTermsAndConditions()
					.tapSignUpButton()