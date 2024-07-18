import internal.GlobalVariable
import katalon.android.common.HomePage
import katalon.android.common.SignInPage
import katalon.android.patient.BookAppointmentPage
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.AppointmentService
import katalon.services.SignInService
import katalon.services.SpecialtyManagementService
import katalon.util.DateUtil

String validTimeSlot, validDate, validSpeciality, dateInTheFuture
String specialtyName = "Dermatology"
def specialty, doctor

'Get account to login'
Credential admin = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

'Use API to get specialty "Dermatology"'
'Log in as admin'
String adminAccessToken = Page.nav(SignInService)
							  .initRequestObject()
							  .signIn(admin.email, admin.pwd)
							  .getAccessToken()
							  
'Use API to get specialty by name'
specialty = Page.nav(SpecialtyManagementService)
				.initRequestObject()
				.getSpecialtyByName(adminAccessToken, specialtyName)

'Activate specialty "Dermatology"'
Page.nav(SpecialtyManagementService)
	.initRequestObject()
	.activateSpecialty(adminAccessToken, specialty.id)
	.verifyStatusCode(200)

'Log in as patient'
Credential firstPatient = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.withGender("Male")
	.getFirst()

String patientAccessToken = Page.nav(SignInService).initRequestObject()
					   			.signIn(firstPatient.email, firstPatient.pwd)
								.getAccessToken()

'Find available date and time slot'
while(true) {
	dateInTheFuture = Page.nav(DateUtil).generateDateInFuture(1, 14)
	
	'Get list of appointment time slots'
	def timeSlotResponse = Page.nav(AppointmentService).initRequestObject()
											   .getAppointmentTimeSlot(dateInTheFuture, specialty.id, patientAccessToken)
											   .parseResponseBodyToJsonObject()
	
	'Change date if you already booked that speciality on that day'
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
	
	validDate = Page.nav(BookAppointmentPage).getTimeSlotDateByAppointmentFormat(dateInTheFuture)
	validSpeciality = specialty.name.toString()
	validTimeSlot = availableTimeSlot.bookingTimeSlot.startTime.toString().substring(0, 5)
	break
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
	.waitForDateVisible(dateForSwiping)

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
	.tapChooseAnotherDoctor()
	.verifyNotiMessage("Only one doctor available left")
	
'Get the doctor name to verify confirmation page'
String validDoctor = Page.nav(BookAppointmentPage)
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

'Log out'
Page.nav(HomePage)
	.tapProfilePicture()
	.tapSignOut()
	
'Verify time slot is disable when there are not any available doctors'
'Log in as patient 2'
Credential secondPatient = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.withGender("Female")
	.getFirst()

Page.nav(SignInPage)
	.setEmail(secondPatient.email)
	.setPassword(secondPatient.pwd)
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

'Choose speciality and wait until the date that match tomorrow is visible'
Page.nav(BookAppointmentPage)
	.tapSpeciality(validSpeciality)
	.tapNext()
	.waitForDateVisible(dateForSwiping)

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