import com.kms.katalon.util.CryptoUtil

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

'User enter invalid email'
Page.nav(SignUpPage).setEmail("user")
					.verifyErrorMessage("Please enter a valid email address (e.g., example@gmail.com)")
					
					.setEmail("user@gmail.com")
					.setFullName("Full Name")
					
'User enter invalid phone number'
Page.nav(SignUpPage).setCellPhoneNumber("0")
					.verifyErrorMessage("Please enter a valid Vietnam phone number (e.g., 0912345678 or 912345678)")
					
					.setCellPhoneNumber("0909123456")
					
'User enter invalid password'
Page.nav(SignUpPage).setPassword(CryptoUtil.encode(CryptoUtil.getDefault('password')))
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword(CryptoUtil.encode(CryptoUtil.getDefault('Password')))
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword(CryptoUtil.encode(CryptoUtil.getDefault('PASSWORD')))
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword(CryptoUtil.encode(CryptoUtil.getDefault('Passw0rd')))
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword(CryptoUtil.encode(CryptoUtil.getDefault('P@ss')))
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword(CryptoUtil.encode(CryptoUtil.getDefault('11111111')))
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")
					
					.setPassword(CryptoUtil.encode(CryptoUtil.getDefault('@@@@@@@@')))
					.verifyErrorMessage("Craft a password: 8+ chars, mix of upper/lowercase, letters/numbers, and 1 special character (e.g., ! @ # ?)")