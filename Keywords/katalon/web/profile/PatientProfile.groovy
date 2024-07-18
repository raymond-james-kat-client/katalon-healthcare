package katalon.web.profile

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import katalon.fw.lib.BasePageWeb

public class PatientProfile extends BasePageWeb<PatientProfile> {
	def genderSelect = { gender -> css("li[data-value='${gender}']") }
	TestObject genderDropdown = css("div[data-testid='patient-formUpdate-gender']")
	TestObject updateProfileBtn = btn("update profile")
	TestObject fullNameInput = id("fullNameInput")
	TestObject phoneNumberInput = id("phoneNumberInput")
	TestObject birthdayInput = placeholder("DD-MM-YYYY")
	TestObject heightInput = id("heightInput")
	TestObject weightInput = id("weightInput")
	TestObject addressInput = id("addressInput")
	TestObject resetBtn = btn("reset")

	public PatientProfile clickGenderDropdown() {
		WebUI.click(genderDropdown)
		return this
	}

	public PatientProfile clickUpdateProfileButton() {
		WebUI.click(updateProfileBtn)
		return this
	}

	public PatientProfile inputFullName(String fullName) {
		clearTextAndSendKeysByActions(fullNameInput, fullName)
		return this
	}

	public PatientProfile inputPhoneNumber(String phoneNumber) {
		clearTextAndSendKeysByActions(phoneNumberInput, phoneNumber)
		return this
	}

	public PatientProfile inputBirthday(String birthday) {
		clearTextAndSendKeys(birthdayInput, birthday)
		return this
	}

	public PatientProfile selectGender(String gender) {
		WebUI.click(genderSelect(gender))
		return this
	}

	public PatientProfile inputHeight(String height) {
		clearTextAndSendKeysByActions(heightInput, height)
		return this
	}

	public PatientProfile inputWeight(String weight) {
		clearTextAndSendKeysByActions(weightInput, weight)
		return this
	}

	public PatientProfile inputAddress(String address) {
		clearTextAndSendKeysByActions(addressInput, address)
		return this
	}

	public PatientProfile clickResetButton() {
		WebUI.click(resetBtn)
		return this
	}

	public PatientProfile verifyFullName(String expectedFullName) {
		String actualFullName = getFullName()
		WebUI.verifyEqual(actualFullName, expectedFullName )
		return this
	}

	public PatientProfile verifyPhoneNumber(String expectedPhoneNumber) {
		String actualPhoneNumber = getPhoneNumber()
		WebUI.verifyEqual(actualPhoneNumber, expectedPhoneNumber )
		return this
	}

	public PatientProfile verifyBirthday(String expectedBirthday) {
		String actualBirthday = getBirthday()
		WebUI.verifyEqual(actualBirthday, expectedBirthday )
		return this
	}

	public PatientProfile verifyGender(String expectedGender) {
		String actualGender = getGender()
		WebUI.verifyEqual(actualGender, expectedGender)
		return this
	}

	public PatientProfile verifyHeight(String expectedHeight) {
		String actualHeight = getHeight()
		WebUI.verifyEqual(actualHeight, expectedHeight )
		return this
	}

	public PatientProfile verifyWeight(String expectedWeight) {
		String actualWeight = getWeight()
		WebUI.verifyEqual(actualWeight, expectedWeight)
		return this
	}

	public PatientProfile verifyAddress(String expectedAddress) {
		String actualAddress = getAddress()
		WebUI.verifyEqual(actualAddress, expectedAddress)
		return this
	}

	public String getFullName() {
		return WebUI.getAttribute(fullNameInput, "value")
	}

	public String getPhoneNumber() {
		return WebUI.getAttribute(phoneNumberInput, "value")
	}

	public String getBirthday() {
		return WebUI.getAttribute(birthdayInput, "value")
	}

	public String getGender() {
		return WebUI.getText(css("div[aria-haspopup='listbox']"))
	}

	public String getHeight() {
		return WebUI.getAttribute(heightInput, "value")
	}

	public String getWeight() {
		return WebUI.getAttribute(weightInput, "value")
	}

	public String getAddress() {
		return WebUI.getAttribute(addressInput, "value")
	}
}
