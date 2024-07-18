import katalon.android.common.HomePage
import katalon.android.common.SignInPage
import katalon.android.patient.BookAppointmentPage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.AppointmentService
import katalon.services.SignInService
import katalon.services.SpecialtyManagementService
import katalon.util.DateUtil

String validTimeSlot, validDate, validDoctor, validSpeciality, dateInTheFuture
String specialtyName = "Diabetology"
def specialty

'Use API to get specialty "Diabetology"'
'Log in as admin'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

String adminAccessToken = Page.nav(SignInService)
							  .initRequestObject()
							  .signIn(user.email, user.pwd)
							  .getAccessToken()
							  
'Use API to get specialty by name'
specialty = Page.nav(SpecialtyManagementService)
				.initRequestObject()
				.getSpecialtyByName(adminAccessToken, specialtyName)

'Activate specialty "Diabetology"'
Page.nav(SpecialtyManagementService)
	.initRequestObject()
	.activateSpecialty(adminAccessToken, specialty.id)
	.verifyStatusCode(200)

'Use API to find time slots and available doctors'
'Log in as patient'
user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.getFirst()

String patientAccessToken = Page.nav(SignInService).initRequestObject()
							    .signIn(user.email, user.pwd)
							    .getAccessToken()

'Find available doctor'
while(true) {
	dateInTheFuture = Page.nav(DateUtil).generateDateInFuture(1, 14)
	
	'Get list of appointment time slots'
	def timeSlotResponse = Page.nav(AppointmentService).initRequestObject()
													   .getAppointmentTimeSlot(dateInTheFuture, specialty.id.toString(), patientAccessToken)
													   .parseResponseBodyToJsonObject()
	
	'Change date and speciality if you already booked that speciality on that day'
	if(timeSlotResponse.message.compareTo("You have booked this specialty on this day, please choose another day!") == 0) {
		continue
	}
											   									   
	boolean check = false
	'Change time slot if it is unavailable'
	def availableTimeSlot
	for (timeSlot in timeSlotResponse.data) {
		if(timeSlot.available == false) {
			continue
		}
		availableTimeSlot = timeSlot
		check = true
		break
	}
	
	if(!check) {
		continue
	}
	
	'Get list of available doctors by time-slot and speciality'
	def doctorList = Page.nav(AppointmentService).initRequestObject()
											 .getAvailableDoctorByTimeSlot(dateInTheFuture, specialty.id.toString(), availableTimeSlot.bookingTimeSlot.id.toString(), patientAccessToken)
											 .parseResponseBodyToJsonObject().data
	
	'Choose date that has more than 1 available doctor'
	if(doctorList.size() < 2)
	{
		continue
	}
	else
	{
		validDate = Page.nav(BookAppointmentPage).getTimeSlotDateByAppointmentFormat(dateInTheFuture)
		validSpeciality = specialty.name.toString()
		validTimeSlot = availableTimeSlot.bookingTimeSlot.startTime.toString().substring(0, 5)
		break
	}
}

'Log in as patient'
Page.nav(SignInPage)
	.setEmail(user.email)
	.setPassword(user.pwd)
	.tapSignIn()

Page.nav(HomePage).tapPrimaryCare()

'Swipe until finding valid speciality'
while(true) {
	if(Page.nav(BookAppointmentPage).verifySpecialityIsVisible(validSpeciality)) {
		break
	}
	Page.nav(BookAppointmentPage)
		.swipeSpeciality()
}

String dateForSwiping = Page.nav(DateUtil).generateDateInFuture(1, 2)
'Choose speciality and wait until the date that match tomorrow is visible'
Page.nav(BookAppointmentPage)
	.tapSpeciality(validSpeciality)
	.tapNext()

'Swipe until finding valid date'
while(true) {
	if(Page.nav(BookAppointmentPage).verifyDateIsVisible(validDate)) {
		break
	}
	
	Page.nav(BookAppointmentPage)
		.swipeDate()
}

'Book appointment but cancel to redirect to home page'
Page.nav(BookAppointmentPage)
	.tapDate(validDate)
	.tapTimeSlot(validTimeSlot)
	.tapFindADoctor()
	.tapCancel()
	.tapFindADoctor()
	.tapChooseAnotherDoctor()
	.tapBookThisDoctor()
	.tapCancel()
	
'Verify appointment booking redirection to home page'
Page.nav(HomePage)
	.verifyHomeButtonIsSelected()

Page.nav(HomePage).tapPrimaryCare()

'Swipe until finding valid speciality'
while(true) {
	if(Page.nav(BookAppointmentPage).verifySpecialityIsVisible(validSpeciality)) {
		break
	}
	Page.nav(BookAppointmentPage)
		.swipeSpeciality()
}

dateForSwiping = Page.nav(DateUtil).generateDateInFuture(1, 2)
'Choose speciality and wait until the date that match tomorrow is visible'
Page.nav(BookAppointmentPage)
	.tapSpeciality(validSpeciality)
	.tapNext()

'Swipe until finding valid date'
while(true) {
	if(Page.nav(BookAppointmentPage).verifyDateIsVisible(validDate)) {
		break
	}
	
	Page.nav(BookAppointmentPage)
		.swipeDate()
}

'Choose date, time, and click find a doctor'
Page.nav(BookAppointmentPage)
	.tapDate(validDate)
	.tapTimeSlot(validTimeSlot)
	.tapFindADoctor()
	
'Get the doctor name to verify confirmation page'
validDoctor = Page.nav(BookAppointmentPage)
				  .getDoctorName()
				  
'Book appointment'
Page.nav(BookAppointmentPage)
	.tapBookThisDoctor()
	.tapEditNote()
	.setNote("I have a headache")
	.verifyService("Primary care")
	.verifySpeciality(validSpeciality)
	.verifyDoctor(validDoctor)
	.verifyDateAndTime(dateInTheFuture, validTimeSlot)
	.tapConfirmAppointment()
	.verifyBookAppointmentSuccessfullyPage()

'Verify appointment booking redirection to home page'
Page.nav(HomePage)
	.verifyHomeButtonIsSelected()

'''
Verify notification after choosing that specialty on that day again
'''
Page.nav(HomePage).tapPrimaryCare()

'Swipe until finding valid speciality'
while(true) {
	if(Page.nav(BookAppointmentPage).verifySpecialityIsVisible(validSpeciality)) {
		break
	}
	Page.nav(BookAppointmentPage)
		.swipeSpeciality()
}

'Choose speciality and wait until the date that match tomorrow is visible'
Page.nav(BookAppointmentPage)
	.tapSpeciality(validSpeciality)
	.tapNext()

'Swipe until finding valid date'
while(true) {
	if(Page.nav(BookAppointmentPage).verifyDateIsVisible(validDate)) {
		break
	}
	
	Page.nav(BookAppointmentPage)
		.swipeDate()
}

'Choose date, time, and click find a doctor'
Page.nav(BookAppointmentPage)
	.tapDate(validDate)
	.verifyNotiMessage("You have booked this specialty on this day, please choose another day!")
	.tapGoBack()
	.tapGoBack()
	
'Deactivate specialty "Diabetology"'
Page.nav(SpecialtyManagementService)
	.initRequestObject()
	.deactivateSpecialty(adminAccessToken, specialty.id)
	.verifyStatusCode(200)

'''
Verify that time slot is disable after choosing another specialty on that day
'''
validSpeciality = "Dermatology"

'Get specialty "Dermatology"'
specialty = Page.nav(SpecialtyManagementService)
				.initRequestObject()
				.getSpecialtyByName(adminAccessToken, validSpeciality)
							  
'Activate specialty "Dermatology"'
Page.nav(SpecialtyManagementService)
	.initRequestObject()
	.activateSpecialty(adminAccessToken, specialty.id)
	.verifyStatusCode(200)

Page.nav(HomePage).tapPrimaryCare()

'Swipe until finding valid speciality'
while(true) {
	if(Page.nav(BookAppointmentPage).verifySpecialityIsVisible(validSpeciality)) {
		break
	}
	Page.nav(BookAppointmentPage)
		.swipeSpeciality()
}

'Choose speciality and wait until the date that match tomorrow is visible'
Page.nav(BookAppointmentPage)
	.tapSpeciality(validSpeciality)
	.tapNext()

'Swipe until finding valid date'
while(true) {
	if(Page.nav(BookAppointmentPage).verifyDateIsVisible(validDate)) {
		break
	}
	
	Page.nav(BookAppointmentPage)
		.swipeDate()
}

'Choose date, time, and click find a doctor'
Page.nav(BookAppointmentPage)
	.tapDate(validDate)
	.verifyTimeSlotIsDisable(validTimeSlot)
	.tapGoBack()
	.tapGoBack()
	
'Log out'
Page.nav(HomePage)
	.tapProfilePicture()
	.tapSignOut()
	
'Deactivate specialty "Dermatology"'
Page.nav(SpecialtyManagementService)
	.initRequestObject()
	.deactivateSpecialty(adminAccessToken, specialty.id)
	.verifyStatusCode(200)