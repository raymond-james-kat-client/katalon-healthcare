package katalon.web.admin

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import katalon.fw.lib.BasePageWeb
import katalon.fw.lib.Page

public class Specialty extends BasePageWeb<Specialty> {
	String rowItem = "//div[contains(@class, 'MuiDataGrid-row')]"
	TestObject titleInput = id("specTitlte")
	TestObject descInput = id("specDesc")
	TestObject addNewSpecialityBtn = btn("add new speciality")
	TestObject confirmBtn = btn("Confirm")
	TestObject updateBtn = css("li[data-testid='edit-button']")
	
	public Specialty clickAddNewSpecialty() {
		WebUI.click(addNewSpecialityBtn)
		return this
	}

	public Specialty inputTitle(String title) {
		clearTextAndSendKeysByActions(titleInput, title)
		return this
	}

	public Specialty inputDescription(String description) {
		clearTextAndSendKeysByActions(descInput, description)
		return this
	}

	public Specialty verifySpecialtyNameInEditDialog(String expectedSpecialtyName) {
		String actualSpecialtyname = WebUI.getAttribute(titleInput, "value")
		WebUI.verifyEqual(actualSpecialtyname, expectedSpecialtyName)
		return this
	}

	public Specialty verifySpecialtyDescriptionInEditDialog(String expectedSpecialtyDescription) {
		String actualSpecialtyDescription = WebUI.getAttribute(descInput, "value")
		WebUI.verifyEqual(actualSpecialtyDescription, expectedSpecialtyDescription)
		return this
	}

	public Specialty clickConfirm() {
		WebUI.click(confirmBtn)
		return this
	}

	public String getSpecialtyName(Number index) {
		String specialtyName = WebUI.getText(xpath("//div[contains(@class, 'MuiDataGrid-row') and @data-rowindex = '${index}']//div[@data-field='name']"))
		return specialtyName
	}

	public String getSpecialtyDescription(Number index) {
		String specialtyDescription = WebUI.getText(xpath("//div[contains(@class, 'MuiDataGrid-row') and @data-rowindex = '${index}']//div[@data-field='description']"))
		return specialtyDescription
	}

	public String getSpecialtyStatus(Number index) {
		String specialtyStatus = WebUI.getText(xpath("//div[contains(@class, 'MuiDataGrid-row') and @data-rowindex = '${index}']//div[@data-field='activated']"))
		return specialtyStatus
	}

	public Specialty clickSpecialtyTripleDot(Number index) {
		WebUI.click(xpath("//div[contains(@class, 'MuiDataGrid-row') and @data-rowindex = '${index}']//div[@data-field='action']//*"))
		return this
	}

	public Specialty clickUpdateButton() {
		WebUI.click(updateBtn)
		return this
	}

	public Number getRandomSpecialtyIndex() {
		Random random = new Random()
		List<TestObject> specialtyList = Page.nav(Specialty).findTestObjects(rowItem)
		Number randomIndex = random.nextInt(specialtyList.size()-1)
		return randomIndex
	}

	public Specialty verifySpecialtyName(Number index, String expectedSpecialtyName) {
		String actualSpecialtyName = Page.nav(Specialty).getSpecialtyName(index)
		WebUI.verifyEqual(expectedSpecialtyName, actualSpecialtyName)
		return this
	}

	public Specialty verifySpecialtyDescription(Number index, String expectedSpecialtyDescription) {
		String actualSpecialtyDescription = Page.nav(Specialty).getSpecialtyDescription(index)
		WebUI.verifyEqual(expectedSpecialtyDescription, actualSpecialtyDescription)
		return this
	}

	public Specialty verifySpecialtyStatus(Number index, String expectedSpecialtyStatus) {
		String actualSpecialtyStatus = Page.nav(Specialty).getSpecialtyStatus(index)
		WebUI.verifyEqual(expectedSpecialtyStatus, actualSpecialtyStatus)
		return this
	}

	public Specialty clickDeactivate() {
		WebUI.click(xpath("//li[@data-testid='deactivate-button' and text()='Deactivate']"))
		return this
	}

	public Specialty clickActivate() {
		WebUI.click(xpath("//li[@data-testid='deactivate-button' and text()='Activate']"))
		return this
	}

	public Specialty deactivateSpecialty(String specialtyName) {
		clickDeactivate()
		inputSpecialtyTitle(specialtyName)
		WebUI.click(byType("submit"))
		return this
	}

	public Specialty activateSpecialty(String specialtyName) {
		clickActivate()
		inputSpecialtyTitle(specialtyName)
		WebUI.click(byType("submit"))
		return this
	}

	public Specialty inputSpecialtyTitle(String specialtyTitle) {
		WebUI.sendKeys(id("matchedSpecialistName"), specialtyTitle)
		return this
	}
}
