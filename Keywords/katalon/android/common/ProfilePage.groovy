package katalon.android.common

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import katalon.fw.lib.BasePageMobile

public class ProfilePage extends BasePageMobile<ProfilePage> {

	def field_fullName = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nAddress\nWeight (kg)\nHeight (cm)\nBirthday']/android.widget.EditText[1]")
	def field_phoneNumber = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nAddress\nWeight (kg)\nHeight (cm)\nBirthday']/android.widget.EditText[2]")
	def field_address = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nAddress\nWeight (kg)\nHeight (cm)\nBirthday']/android.widget.EditText[3]")
	def field_weight = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nAddress\nWeight (kg)\nHeight (cm)\nBirthday']/android.widget.EditText[4]")
	def field_height = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nAddress\nWeight (kg)\nHeight (cm)\nBirthday']/android.widget.EditText[5]")
	def field_birthday = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nAddress\nWeight (kg)\nHeight (cm)\nBirthday']/android.widget.EditText[6]")

	public ProfilePage verifyCorrectFullName(String expectedFullName) {
		Mobile.verifyElementText(field_fullName, expectedFullName)
		return this
	}

	public ProfilePage verifyCorrectGender(String expectedGender) {
		verifyElementAttribute(xpath("//android.view.View[@content-desc= '${expectedGender}']/android.widget.RadioButton"), "checked", "true")
		def otherGender = "Male".equals(expectedGender) ? "Female" : "Male"
		verifyElementAttribute(xpath("//android.view.View[@content-desc= '${otherGender}']/android.widget.RadioButton"), "checked", "false")
		return this
	}

	public ProfilePage verifyCorrectPhoneNumber(String expectedPhoneNumber) {
		if (expectedPhoneNumber.charAt(0) == "0") {
			Mobile.verifyElementText(field_phoneNumber, expectedPhoneNumber.substring(1))
		}
		else {
			Mobile.verifyElementText(field_phoneNumber, expectedPhoneNumber)
		}
		return this
	}

	public ProfilePage verifyCorrectAddress(String expectedAddress) {
		Mobile.verifyElementText(field_address, expectedAddress)
		return this
	}

	public ProfilePage verifyCorrectWeight(String expectedWeight) {
		Mobile.verifyElementText(field_weight, expectedWeight)
		return this
	}

	public ProfilePage verifyCorrectHeight(String expectedHeight) {
		Mobile.verifyElementText(field_height, expectedHeight)
		return this
	}

	public ProfilePage verifyCorrectBirthday(String expectedBirthday) {
		Mobile.verifyElementText(field_birthday, expectedBirthday)
		return this
	}

	public ProfilePage tapSaveChanges() {
		tap(accessibilityid("Save changes"))
		return this
	}

	public ProfilePage goBackToHomePage() {
		tap(accessibilityid("Back"))
		return this
	}

	public ProfilePage setFullName(String fullName) {
		tap(field_fullName)
		setText(field_fullName, fullName)
		hideKeyboard()
		return this
	}

	public ProfilePage chooseGender(String expectedGender) {
		checkElement(xpath("//android.view.View[@content-desc= '${expectedGender}']/android.widget.RadioButton"))
		return this
	}

	public ProfilePage setPhoneNumber (String phoneNumber) {
		tap(field_phoneNumber)
		setText(field_phoneNumber, phoneNumber)
		hideKeyboard()
		return this
	}

	public ProfilePage setAddress (String address) {
		tap(field_address)
		setText(field_address, address)
		hideKeyboard()
		return this
	}

	public ProfilePage setWeight (String weight) {
		tap(field_weight)
		setText(field_weight, weight)
		hideKeyboard()

		return this
	}

	public ProfilePage setHeight (String height) {
		tap(field_height)
		setText(xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nAddress\nWeight (kg)\nHeight (cm)\nBirthday']/android.widget.EditText[5]"), height)
		hideKeyboard()
		return this
	}

	public ProfilePage tapIncrease1kg () {
		tap(xpath("//android.widget.EditText[4]/android.widget.Button[1]"))
		return this
	}

	public ProfilePage tapDecrease1kg () {
		tap(xpath("//android.widget.EditText[4]/android.widget.Button[2]"))
		return this
	}

	public ProfilePage tapIncrease1cm () {
		tap(xpath("//android.widget.EditText[5]/android.widget.Button[1]"))
		return this
	}

	public ProfilePage tapDecrease1cm () {
		tap(xpath("//android.widget.EditText[5]/android.widget.Button[2]"))
		return this
	}

	public ProfilePage setBirthday(String birthday) {
		tap(field_birthday)
		setText(xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nAddress\nWeight (kg)\nHeight (cm)\nBirthday']/android.widget.EditText[5]"), birthday)
		hideKeyboard()
		return this
	}

	public ProfilePage verifyUpdateSuccessfully () {
		verifyElementExist(accessibilityid("Update profile successfully"))
		return this
	}

	public ProfilePage verifyCorrectErrorMessage (String expectedErrorMessage) {
		verifyElementExist(accessibilityid(expectedErrorMessage))
		return this
	}

	public ProfilePage tapCancel() {
		tap(accessibilityid('CANCEL'))
		return this
	}

	public ProfilePage tapOK() {
		tap(accessibilityid('OK'))
		return this
	}
}