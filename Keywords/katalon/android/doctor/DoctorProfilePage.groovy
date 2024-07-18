package katalon.android.doctor

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import katalon.fw.lib.BasePageMobile

public class DoctorProfilePage extends BasePageMobile<DoctorProfilePage> {

	def field_fullName = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nSpeciality\nDegree\nAddress\nBirthday\nDescription']/android.widget.EditText[1]")
	def field_phoneNumber = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nSpeciality\nDegree\nAddress\nBirthday\nDescription']/android.widget.EditText[1]")
	def field_address = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nSpeciality\nDegree\nAddress\nBirthday\nDescription']/android.widget.EditText[2]")
	def field_birthday = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nSpeciality\nDegree\nAddress\nBirthday\nDescription']/android.widget.EditText[3]")
	def field_description = xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nSpeciality\nDegree\nAddress\nBirthday\nDescription']/android.widget.EditText[4]")

	public DoctorProfilePage verifyCorrectFullName(String expectedFullName) {
		Mobile.verifyElementText(field_fullName, expectedFullName)
		return this
	}

	public DoctorProfilePage verifyCorrectGender(String expectedGender) {
		verifyElementAttribute(xpath("//android.view.View[@content-desc= '${expectedGender}']/android.widget.RadioButton"), "checked", "true")
		Mobile.verifyElementChecked(xpath("//android.view.View[@content-desc= '${expectedGender}']/android.widget.RadioButton"), 0)
		def otherGender = "Male".equals(expectedGender) ? "Female" : "Male"
		verifyElementAttribute(xpath("//android.view.View[@content-desc= '${otherGender}']/android.widget.RadioButton"), "checked", "false")
		return this
	}

	public DoctorProfilePage verifyCorrectPhoneNumber(String expectedPhoneNumber) {
		if (expectedPhoneNumber.charAt(0) == "0") {
			Mobile.verifyElementText(field_phoneNumber, expectedPhoneNumber.substring(1))
		}
		else {
			Mobile.verifyElementText(field_phoneNumber, expectedPhoneNumber)
		}
		return this
	}

	public DoctorProfilePage verifyCorrectAddress(String expectedAddress) {
		Mobile.verifyElementText(field_address, expectedAddress)
		return this
	}

	public DoctorProfilePage verifyCorrectBirthday(String expectedBirthday) {
		Mobile.verifyElementText(field_birthday, expectedBirthday)
		return this
	}

	public DoctorProfilePage tapSaveChanges() {
		tap(accessibilityid("Save changes"))
		return this
	}

	public DoctorProfilePage goBackToHomePage() {
		tap(accessibilityid("Back"))
		return this
	}

	public DoctorProfilePage setFullName(String fullName) {
		tap(field_fullName)
		setText(field_fullName, fullName)
		hideKeyboard()
		return this
	}

	public DoctorProfilePage chooseGender(String expectedGender) {
		checkElement(xpath("//android.view.View[@content-desc= '${expectedGender.toLowerCase().capitalize()}']/android.widget.RadioButton"))
		return this
	}

	public DoctorProfilePage setPhoneNumber (String phoneNumber) {
		tap(field_phoneNumber)
		if (phoneNumber.startsWith("+")) {
			setText(field_phoneNumber, phoneNumber.substring(3))
		}
		else {
			setText(field_phoneNumber, phoneNumber)
		}
		hideKeyboard()
		return this
	}

	public DoctorProfilePage setAddress (String address) {
		tap(field_address)
		setText(field_address, address)
		hideKeyboard()
		return this
	}

	public DoctorProfilePage setBirthday(String birthday) {
		tap(field_birthday)
		setText(field_birthday, birthday)
		hideKeyboard()
		return this
	}

	public DoctorProfilePage setDescription(String description) {
		tap(field_description)
		setText(xpath("//android.view.View[@content-desc='Full name\nGender\nCell phone number\nSpeciality\nDegree\nAddress\nBirthday\nDescription']/android.widget.EditText[3]"), description)
		hideKeyboard()
		return this
	}

	public DoctorProfilePage verifyRequestSent () {
		verifyElementVisible(accessibilityid("Sent your request change successfully"))
		return this
	}

	public DoctorProfilePage verifyCorrectErrorMessage (String expectedErrorMessage) {
		verifyElementVisible(accessibilityid(expectedErrorMessage))
		return this
	}

	public DoctorProfilePage chooseSpeciality() {
		Random random = new Random()
		int randomNumber = random.nextInt(4)

		tap(xpath('//android.widget.Button[@index=1]'), 0)
		tap(xpath("//android.view.View[@index=$randomNumber]"))
		return this
	}

	public DoctorProfilePage chooseDegree() {
		Random random = new Random()
		int randomNumber = random.nextInt(4)

		tap(xpath('//android.widget.Button[@index=2]'), 0)
		tap(xpath("//android.view.View[@index=$randomNumber]"))
		return this
	}

	public DoctorProfilePage swipeToBottom() {
		Mobile.swipe(500, (Mobile.getDeviceHeight()*0.7).toInteger(), 0, (Mobile.getDeviceHeight()*0.3).toInteger())
		return this
	}
}