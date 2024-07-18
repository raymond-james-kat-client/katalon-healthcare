package katalon.android.patient

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

import io.appium.java_client.MobileElement
import katalon.fw.lib.BasePageMobile
import katalon.fw.lib.Page
import katalon.util.DateUtil

public class BookAppointmentPage extends BasePageMobile<BookAppointmentPage> {
	public String getTimeSlotDateByAppointmentFormat(String dateString) {
		List<String> dateSplit = dateString.split("-")
		int year = dateSplit[0].toInteger()
		int month = dateSplit[1].toInteger()
		int dayOfTheMonth = dateSplit[2].toInteger()
		LocalDate date = LocalDate.of(year, month, dayOfTheMonth)
		String dayOfTheWeek = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)).substring(0, 3);
		return (dayOfTheMonth < 10 ? "0" + dayOfTheMonth.toString() : dayOfTheMonth.toString()) + "/" + (month < 10 ? "0" + month.toString() : month.toString()) + "\n" + dayOfTheWeek
	}

	public BookAppointmentPage tapSpeciality(String name) {
		tap(accessibilityid(name))
		return this
	}

	public BookAppointmentPage tapNext() {
		tap(accessibilityid("Next"))
		return this
	}

	public BookAppointmentPage tapDate(String date) {
		tap(accessibilityid(date))
		return this
	}

	public BookAppointmentPage tapTimeSlot(String timeSlot) {
		tap(accessibilityid(timeSlot))
		return this
	}

	public BookAppointmentPage tapFindADoctor() {
		tap(accessibilityid("Find a doctor"))
		return this
	}

	public BookAppointmentPage tapBookThisDoctor() {
		tap(accessibilityid("Book this doctor"))
		return this
	}

	public BookAppointmentPage tapChooseAnotherDoctor() {
		tap(accessibilityid("Choose another doctor"))
		return this
	}

	public BookAppointmentPage tapCancel() {
		tap(accessibilityid("Cancel"))
		return this
	}

	public BookAppointmentPage tapConfirmAppointment() {
		tap(accessibilityid("Confirm Appointment"))
		return this
	}

	public BookAppointmentPage tapEditNote() {
		tap(xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[12]"))
		return this
	}

	public BookAppointmentPage tapGoBack() {
		tap(accessibilityid("Back"))
		return this
	}

	public BookAppointmentPage setNote(String note) {
		setText(cls("android.widget.EditText"), note)
		hideKeyboard()
		return this
	}

	public String getDoctorName() {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByClassName("android.widget.ImageView")
		String content = elements.get(0).getAttribute("content-desc")
		String doctorName = content.split("Dr. ")[1].split("\n")[0]
		return doctorName
	}

	public BookAppointmentPage swipeSpeciality() {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByClassName("android.widget.RadioButton")
		def firstElement = elements.get(0)
		def secondElement = elements.get(1)
		swipe(secondElement.getLocation().x, secondElement.getLocation().y, firstElement.getLocation().x, firstElement.getLocation().y)
		return this
	}

	public String swipeDate() {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByClassName("android.widget.Button")
		def firstElement = elements.get(1)
		def secondElement = elements.get(2)
		swipe(secondElement.getLocation().x + 20, secondElement.getLocation().y + 50, firstElement.getLocation().x + 20, firstElement.getLocation().y + 50)
		return this
	}

	public boolean verifyDateIsVisible(String date) {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByXPath("//android.widget.Button[@content-desc='Find a doctor']/preceding-sibling::android.view.View[2]//*[(@content-desc)]")
		for (ele in elements) {
			if(ele.getAttribute("content-desc").compareTo(date) == 0) {
				return true
			}
		}
		return false
	}

	public boolean verifySpecialityIsVisible(String speciality) {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByClassName("android.widget.RadioButton")
		for (ele in elements) {
			if(ele.getAttribute("content-desc").compareTo(speciality) == 0) {
				return true
			}
		}
		return false
	}

	public BookAppointmentPage verifyTimeSlotIsDisable(String timeSlot) {
		MobileElement ele = getCurrentSessionMobileDriver().findElementByAccessibilityId(timeSlot)
		verifyEqual(ele.getAttribute("clickable"), "false")
		verifyEqual(ele.getAttribute("enabled"), "false")
		return this
	}

	public BookAppointmentPage verifyService(String expectedValue) {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByXPath("(//android.view.View[@content-desc='Service']/following-sibling::android.view.View)[1]/*")
		verifyElementAttribute(elements.get(0), "content-desc", expectedValue)
		return this
	}

	public BookAppointmentPage verifyDoctor(String expectedValue) {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByXPath("(//android.view.View[@content-desc='Doctor']/following-sibling::android.view.View)[1]/*")
		verifyElementAttribute(elements.get(0), "content-desc", expectedValue)
		return this
	}

	public BookAppointmentPage verifySpeciality(String expectedValue) {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByXPath("(//android.view.View[@content-desc='Speciality']/following-sibling::android.view.View)[1]/*")
		verifyElementAttribute(elements.get(0), "content-desc", expectedValue)
		return this
	}

	public BookAppointmentPage verifyDateAndTime(String date, String time) {
		String dateTime = time + " | " + Page.nav(DateUtil).changeDateFormat(date, "dd-MM-yyyy")
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByXPath("(//android.view.View[@content-desc='Date & Time']/following-sibling::android.view.View)[1]/*")
		verifyElementAttribute(elements.get(0), "content-desc", dateTime)
		return this
	}

	public BookAppointmentPage verifyBookAppointmentSuccessfullyPage() {
		String message = "Book appointment successfully"
		MobileElement element = getCurrentSessionMobileDriver().findElementByAccessibilityId(message)
		verifyElementAttribute(element, "content-desc", message)
		return this
	}

	public BookAppointmentPage verifyNotiMessage(String message) {
		List<MobileElement> elements = getCurrentSessionMobileDriver().findElementsByAccessibilityId(message)
		verifyElementAttribute(elements.get(0), "content-desc", message)
		return this
	}

	public BookAppointmentPage waitForDateVisible(String date) {
		String timeSlotDate = getTimeSlotDateByAppointmentFormat(date)
		waitForElementPresent(accessibilityid(timeSlotDate))
		return this
	}
}
