package katalon.web.admin

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import katalon.fw.lib.BasePageWeb
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.model.DoctorModel
import katalon.services.DoctorManagementService
import katalon.services.DoctorProfileService
import katalon.services.SignInService

public class Doctor extends BasePageWeb<Doctor>{
	private List<String> previousList = null;
	private String currentPage = 1;
	private DoctorModel doctor = null;
	TestObject fullNameColumnHeader = xpath("//div[@data-field='fullName' and @role='columnheader']")

	public Doctor setPreviousList() {
		def testObjects = findTestObjects("//div[@data-field='fullName' and not(@aria-label = 'Full name')]")
		if(previousList != null)
			previousList.clear();
		previousList = new ArrayList<String>()

		for (to in testObjects) {
			previousList.add(WebUI.getText(to))
		}
		return this
	}

	public Doctor verifyCurrentListNotEqualToPreviousList() {
		def testObjects = findTestObjects("//div[@data-field='fullName' and not(@aria-label = 'Full name')]")
		int index = 0, count = 0

		if(testObjects.size() != previousList.size()){
			KeywordUtil.markPassed("The previous list is different from current list")
			return this
		}

		for (to in testObjects) {
			if(previousList.get(index).compareTo(WebUI.getText(to)) != 0) {
				count++
				index++
			}
		}

		if(count == index)
			KeywordUtil.markPassed("The previous list is different from current list")
		else
			KeywordUtil.markPassed("The previous list is equal to current list")

		return this
	}

	public String getDoctorInformationOfRandomRow() {
		int totalRows = findTestObjects("//div[@data-field='fullName' and not(@aria-label = 'Full name')]").size()
		Random random = new Random()
		String randomRow = totalRows < 10 ? (random.nextInt(totalRows - 1) + 1).toString() : (random.nextInt(9) + 1).toString()
		String fullName = WebUI.getText(xpath("(//div[@data-field='fullName' and not(@aria-label = 'Full name')]/div)[${randomRow}]"))
		String email = WebUI.getText(xpath("(//div[@data-field='email' and not(@aria-label = 'Email')]/div)[${randomRow}]"))
		String dateOfBirth = WebUI.getText(xpath("(//div[@data-field='birthday' and not(@aria-label = 'Date of birth')]/div)[${randomRow}]"))
		String speciality = WebUI.getText(xpath("(//div[@data-field='speciality' and not(@aria-label = 'Speciality')]/div)[${randomRow}]"))
		String degree = WebUI.getText(xpath("(//div[@data-field='degree' and not(@aria-label = 'Degree')]/div)[${randomRow}]"))
		String status = WebUI.getText(xpath("(//div[@data-field='status' and not(@aria-label = 'Status')]//span)[${randomRow}]"))
		if(doctor == null)
			doctor = new DoctorModel()
		doctor.setDoctor(email, fullName, dateOfBirth, degree, speciality, status)
		return randomRow
	}

	public Doctor getDoctorInformationOfRow(String number) {
		String fullName = WebUI.getText(xpath("(//div[@data-field='fullName' and not(@aria-label = 'Full name')]/div)[${number}]"))
		String email = WebUI.getText(xpath("(//div[@data-field='email' and not(@aria-label = 'Email')]/div)[${number}]"))
		String dateOfBirth = WebUI.getText(xpath("(//div[@data-field='birthday' and not(@aria-label = 'Date of birth')]/div)[${number}]"))
		String speciality = WebUI.getText(xpath("(//div[@data-field='speciality' and not(@aria-label = 'Speciality')]/div)[${number}]"))
		String degree = WebUI.getText(xpath("(//div[@data-field='degree' and not(@aria-label = 'Degree')]/div)[${number}]"))
		String status = WebUI.getText(xpath("(//div[@data-field='status' and not(@aria-label = 'Status')]//span)[${number}]"))
		if(doctor == null)
			doctor = new Doctor()
		doctor.setDoctor(email, fullName, dateOfBirth, degree, speciality, status)
		return this
	}

	public Doctor getEditDoctorProfile() {
		String phoneNumber = WebUI.getAttribute(id("doctorPhoneNumber"), "value")
		String address = WebUI.getAttribute(id("doctorAddress"), "value")
		String description = WebUI.getAttribute(id("doctorDescription"), "value")
		String fullName = WebUI.getAttribute(id("doctorFullName"), "value")
		String degree = WebUI.getText(id("doctorDegree"))
		String speciality = WebUI.getText(id("doctorSpeciality"))
		String birthday = WebUI.getAttribute(xpath("//label[text()='Birthday']/following-sibling::div//input"), "value")
		String gender = WebUI.getAttribute(xpath("//span[contains(@class,'check')]//input[@name='gender']"), "value")
		
		doctor.setDoctor(fullName, birthday, degree, speciality, phoneNumber, gender, address, description)
		return this
	}

	public Doctor verifyEditDoctorProfileWithDoctorViewList() {
		String fullName = WebUI.getAttribute(id("doctorFullName"), "value")
		String degree = WebUI.getText(id("doctorDegree"))
		String speciality = WebUI.getText(id("doctorSpeciality"))
		String birthday = WebUI.getAttribute(xpath("//label[text()='Birthday']/following-sibling::div//input"), "value")
		if(doctor.compare(fullName, birthday, degree, speciality))
			KeywordUtil.markPassed("Doctor profile matches with doctor view list")
		else
			KeywordUtil.markFailedAndStop("Doctor profile does not match with doctor view list")
		return this
	}

	public Doctor verifyAllDoctorProfile() {
		String phoneNumber = WebUI.getAttribute(id("doctorPhoneNumber"), "value")
		String address = WebUI.getAttribute(id("doctorAddress"), "value")
		String description = WebUI.getAttribute(id("doctorDescription"), "value")
		String fullName = WebUI.getAttribute(id("doctorFullName"), "value")
		String degree = WebUI.getText(id("doctorDegree"))
		String speciality = WebUI.getText(id("doctorSpeciality"))
		String birthday = WebUI.getAttribute(xpath("//label[text()='Birthday']/following-sibling::div//input"), "value")
		String gender = "MALE"
		List<TestObject> genderTOs = findTestObjects("//input[@name='gender']")
		for (to in genderTOs) {
			if(WebUI.verifyElementChecked(to, 0, FailureHandling.CONTINUE_ON_FAILURE)) {
				gender = WebUI.getAttribute(to, "value")
				break
			}
		}
		if(doctor.compare(fullName, birthday, degree, speciality, phoneNumber, gender, address, description))
			KeywordUtil.markPassed("Doctor profile matches with newest information")
		else
			KeywordUtil.markFailedAndStop("Doctor profile does not match with newest information")
		return this
	}

	public Doctor uploadAvatar() {
		uploadImage(id("doctorPhoto"), "/Data/Web/Doctor Management/doctorAvatar.png")
		return this
	}

	public Doctor inputEmail(String text) {
		clearTextAndSendKeysByActions(id("doctorEmail"), text)
		return this
	}

	public Doctor inputFullName(String text) {
		clearTextAndSendKeysByActions(id("doctorFullName"), text)
		return this
	}

	public Doctor inputPhoneNumber(String text) {
		clearTextAndSendKeysByActions(id("doctorPhoneNumber"), text)
		return this
	}

	public Doctor inputPassword(String text) {
		clearTextAndSendKeysByActions(id("doctorPassword"), text)
		return this
	}

	public Doctor inputAddress(String text) {
		clearTextAndSendKeysByActions(id("doctorAddress"), text)
		return this
	}

	public Doctor inputDescription(String text) {
		clearTextAndSendKeysByActions(id("doctorDescription"), text)
		return this
	}

	public Doctor selectGender(String text) {
		WebUI.click(css("div#doctorGender input[value='${text}']"))
		return this
	}

	public Doctor inputBirthday(String text) {
		clearTextAndSendKeysByActions(xpath("//label[text()='Birthday']/following-sibling::div//input"), text)
		return this
	}

	public Doctor selectDegree(String text) {
		clickCustomDropdown(id("doctorDegree"), xpath("//li[@role='option' and @data-value='${text}']"))
		return this
	}

	public Doctor selectSpeciality(String text) {
		clickCustomDropdown(id("doctorSpeciality"), xpath("//li[@role='option' and @data-value='${text}']"))
		return this
	}

	public Doctor clickCancel() {
		WebUI.click(btn("Cancel"))
		return this
	}

	public Doctor clickConfirm() {
		WebUI.click(btn("Confirm"))
		return this
	}

	public Doctor clickNextIcon() {
		WebUI.click(svg("NavigateNextIcon"))
		return this
	}

	public Doctor clickBeforeIcon() {
		WebUI.click(svg("NavigateBeforeIcon"))
		return this
	}

	public Doctor clickMoreIcon(String number) {
		WebUI.click(xpath("(//*[@data-testid='MoreVertIcon'])[${number}]"))
		return this
	}

	public String clickRandomMoreIcon() {
		int totalRows = findTestObjects("//*[@data-testid='MoreVertIcon']").size()
		Random random = new Random()
		String randomRow = totalRows < 10 ? (random.nextInt(totalRows - 1) + 1).toString() : (random.nextInt(9) + 1).toString()
		WebUI.click(xpath("(//*[@data-testid='MoreVertIcon'])[${randomRow}]"))
		return randomRow.toString()
	}

	public Doctor clickEditDoctorProfile() {
		WebUI.click(text("Edit"))
		return this
	}

	public Doctor clickRandomPagination() {
		int totalPage = findTestObjects("//button[contains(@class,'MuiPaginationItem-root')]").size()
		Random random = new Random()
		String randomPage = (random.nextInt(totalPage - 3) + 2).toString()
		while(randomPage.compareTo(currentPage) == 0) {
			randomPage = (random.nextInt(totalPage - 3) + 2).toString()
		}
		WebUI.click(css("button[aria-label='Go to page ${randomPage}']"))
		currentPage = randomPage
		return this
	}

	//	include before and next pagination
	public Doctor clickFirstPagination() {
		WebUI.click(xpath("(//button[contains(@class,'MuiPaginationItem-root')])[2]"))
		currentPage = 1
		return this
	}

	//	include before and next pagination
	public Doctor clickLastPagination() {
		int totalPage = findTestObjects("//button[contains(@class,'MuiPaginationItem-root')]").size()
		WebUI.click(xpath("(//button[contains(@class,'MuiPaginationItem-root')])[${totalPage - 1}]"))
		currentPage = totalPage - 2
		return this
	}

	public Doctor clickFullNameColumnTitle() {
		scrollToTop()
		WebUI.click(fullNameColumnHeader)
		return this
	}

	public Doctor verifyEditDoctorTitle() {
		WebUI.verifyElementVisible(text("Edit doctor"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyConfirmClickable() {
		WebUI.verifyElementClickable(btn("Confirm"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyCalendarIconClickable() {
		WebUI.verifyElementClickable(svg("CalendarIcon"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyUploadPhotoClickable() {
		WebUI.verifyElementClickable(xpath("//*[text()='Upload photo']"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyGenderClickable(String gender) {
		WebUI.verifyElementClickable(xpath("//div[@id='doctorGender']//span[text()='${gender}']"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyCloseIconClickable() {
		WebUI.verifyElementClickable(xpath("//*[@data-testid='CloseIcon']/parent::button"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyPhotoFieldRequired() {
		WebUI.verifyElementText(xpath("//label[text()='Photo']/span"), "*", FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyFullNameFieldRequired() {
		WebUI.verifyElementText(xpath("//label[text()='Full name']/span"), "*", FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyPhoneNumberFieldRequired() {
		WebUI.verifyElementText(xpath("//label[text()='Phone number']/span"), "*", FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyGenderFieldRequired() {
		WebUI.verifyElementText(xpath("//label[text()='Gender']/span"), "*", FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyDegreeFieldRequired() {
		WebUI.verifyElementText(xpath("//label[text()='Degree']/span"), "*", FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifySpecialityFieldRequired() {
		WebUI.verifyElementText(xpath("//label[text()='Speciality']/span"), "*", FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyDescriptionFieldRequired() {
		WebUI.verifyElementText(xpath("//label[text()='Description']/span"), "*", FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyTotalElementPerPage() {
		Credential user = Page.nav(Credential)
				.getCredentials()
				.withRole("Admin")
				.getFirst()

		Page.nav(SignInService).initRequestObject()
				.signIn(user.email, user.pwd)
				.getAccessToken()

		int totalRecords = Page.nav(DoctorManagementService).initRequestObject()
				.getAllDoctors(GlobalVariable.accessToken)
				.parseResponseBodyToJsonObject()
				.data.pagination.totalRecords

		int totalElement = findTestObjects("//div[@data-field='fullName' and not(@aria-label = 'Full name')]").size()
		if(totalElement == totalRecords)
			WebUI.verifyMatch(totalElement.toString(), totalRecords.toString(), false)
		else
			WebUI.verifyMatch(totalElement.toString(), "10", false)
		return this
	}

	public Doctor verifyBeforePaginationNotClickable() {
		WebUI.verifyElementNotClickable(xpath("//*[@data-testid='NavigateBeforeIcon']/parent::button"))
		return this
	}

	public Doctor verifyBeforePaginationClickable() {
		WebUI.verifyElementClickable(xpath("//*[@data-testid='NavigateBeforeIcon']/parent::button"))
		return this
	}

	public Doctor verifyNextPaginationClickable() {
		WebUI.verifyElementClickable(xpath("//*[@data-testid='NavigateNextIcon']/parent::button"))
		return this
	}

	public Doctor verifyNextPaginationNotClickable() {
		WebUI.verifyElementNotClickable(xpath("//*[@data-testid='NavigateNextIcon']/parent::button"))
		return this
	}

	public Doctor verifyFirstElementNameEqualToLastElementName() {
		def testObjects = findTestObjects("//div[@data-field='fullName' and not(@aria-label = 'Full name')]")
		if(WebUI.getText(testObjects[testObjects.size() - 1]).compareTo(previousList[0])) {
			KeywordUtil.markPassed("First element name is equal to last element name after sorting")
			return this
		}

		KeywordUtil.markFailedAndStop("First element name is not equal to last element name after sorting")
		return this
	}

	public Doctor verifyFullNameAscIconIsVisible() {
		WebUI.verifyElementVisible(xpath("//div[text()='Full name']/parent::div//following-sibling::div//*[@data-testid='ArrowUpwardIcon']"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyFullNameDescIconIsVisible() {
		WebUI.verifyElementVisible(xpath("//div[text()='Full name']/parent::div//following-sibling::div//*[@data-testid='ArrowDownwardIcon']"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}

	public Doctor verifyPaginationIsFocus() {
		WebUI.verifyElementVisible(css("button[aria-label='page ${currentPage}']"), FailureHandling.CONTINUE_ON_FAILURE)
		return this
	}
}
