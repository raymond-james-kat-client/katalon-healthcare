import internal.GlobalVariable
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.AppointmentService
import katalon.services.SignInService
import katalon.services.SpecialtyManagementService
import katalon.util.DateUtil

def timeSlotList, doctorList
String timeSlotId, doctorId, dateInTheFuture, specialityId
String specialtyName = "Dermatology"
String note = "This is note"

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

'Log in as admin'
String adminAccessToken = Page.nav(SignInService)
							  .initRequestObject()
							  .signIn(user.email, user.pwd)
							  .getAccessToken()

'Use API to get specialty by name'
specialityId = Page.nav(SpecialtyManagementService)
				.initRequestObject()
				.getSpecialtyByName(adminAccessToken, specialtyName).id
							  
'Activate specialty "Dermatology"'
Page.nav(SpecialtyManagementService)
	.initRequestObject()
	.activateSpecialty(adminAccessToken, specialityId)
	.verifyStatusCode(200)

'Log in as patient'
user = Page.nav(Credential)
	.getCredentials()
	.withRole("Patient")
	.getFirst()

def patientAccessToken = Page.nav(SignInService).initRequestObject()
											   .signIn(user.email, user.pwd)
											   .getAccessToken()
	
'Find available doctor'
while(true) {
	dateInTheFuture = Page.nav(DateUtil).generateDateInFuture(1, 14)
	
	'Get list of appointment time slots'
	timeSlotResponse = Page.nav(AppointmentService).initRequestObject()
											   .getAppointmentTimeSlot(dateInTheFuture, specialityId, patientAccessToken)
											   .parseResponseBodyToJsonObject()
	
	'Change date if you already booked that speciality on that day'
	if(timeSlotResponse.message.compareTo("You have booked this specialty on this day, please choose another day!") == 0) {
		continue
	}
	
	boolean check = false
	'Change time slot if it is unavailable'
	for (timeSlot in timeSlotResponse.data) {
		if(timeSlot.available == false) {
			continue
		}
		timeSlotId = timeSlot.bookingTimeSlot.id
		check = true
		break
	}
	
	if(!check) {
		continue
	}
	
	break							   
}

'Get list of available doctors by time-slot and speciality'
doctorList = Page.nav(AppointmentService).initRequestObject()
										 .getAvailableDoctorByTimeSlot(dateInTheFuture, specialityId, timeSlotId, patientAccessToken)
										 .parseResponseBodyToJsonObject().data
										 
doctorId = Page.nav(AppointmentService).getRandomObject(doctorList).id

'Book appointment'
Page.nav(AppointmentService).initRequestObject()
							.bookAppointment(doctorId, timeSlotId, dateInTheFuture, note, patientAccessToken)
							.verifyStatusCode(200)
							.verifyPropertyValue("message", "Book appointment successfully")
							
'Deactivate specialty "Dermatology"'
Page.nav(SpecialtyManagementService)
	.initRequestObject()
	.deactivateSpecialty(adminAccessToken, specialityId)
	.verifyStatusCode(200)