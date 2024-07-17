package katalon.services

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable
import katalon.fw.lib.BaseService

public class AppointmentService extends BaseService<AppointmentService> {
	private String appointmentUrl = GlobalVariable.apiUrl + "appointments"
	private String appointmentTimeSlotUrl = GlobalVariable.apiUrl + "appointments/time-slots"
	private String availableDoctorByTimeSlotUrl = GlobalVariable.apiUrl + "appointments/doctors"
	private String permittedDates = GlobalVariable.apiUrl + "appointments/permitted-dates"

	public AppointmentService getAppointmentList(String token) {
		setUrl(appointmentUrl).setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public AppointmentService getAppointment(String token, String appointmentId) {
		setUrl(appointmentUrl + "/${appointmentId}").setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public AppointmentService bookAppointment(String doctorId, String bookingTimeSlot, String permittedDate, String note, String token) {
		setUrl(appointmentUrl).setBearerAuthorizationHeader(token)
				.setJsonContentTypeHeader()
				.setPayLoad("""
											{
											  "doctor": "$doctorId",
											  "bookingTimeSlot": "$bookingTimeSlot",
											  "date": "$permittedDate",
											  "note": "$note"
											}""")
				.sendPostRequest()
	}

	public AppointmentService getAppointmentTimeSlot(String date, String specialityId, String token) {
		List<TestObjectProperty> parameters = new ArrayList<>()
		parameters.add(new TestObjectProperty('date', ConditionType.EQUALS, date))
		parameters.add(new TestObjectProperty('specialityId', ConditionType.EQUALS, specialityId))

		setUrl(appointmentTimeSlotUrl).setBearerAuthorizationHeader(token)
				.setParam(parameters)
				.sendGetRequest()
	}

	public AppointmentService getAvailableDoctorByTimeSlot(String date, String specialityId, String timeSlot, String token) {
		List<TestObjectProperty> parameters = new ArrayList<>()

		parameters.add(new TestObjectProperty('date', ConditionType.EQUALS, date))
		parameters.add(new TestObjectProperty('specialityId', ConditionType.EQUALS, specialityId))
		parameters.add(new TestObjectProperty('timeSlot', ConditionType.EQUALS, timeSlot))

		setUrl(availableDoctorByTimeSlotUrl).setBearerAuthorizationHeader(token)
				.setParam(parameters)
				.sendGetRequest()
	}

	public AppointmentService getPermittedDates(String token) {
		setUrl(permittedDates).setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public Object getRandomObject(ArrayList<Object> arr) {
		Random random = new Random()
		int totalSize = arr.size()
		return arr[totalSize > 1 ? random.nextInt(totalSize - 1) : 0]
	}

	public String getToday() {
		LocalDate today = LocalDate.now()
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return today.format(formatter)
	}

	public String verifyIntervalOfTimeSlot(List<Object> timeSlotResponseData) {
		for (timeSlot in timeSlotResponseData) {
			int hour = timeSlot.bookingTimeSlot.startTime.substring(0, 2).toInteger()
			int min = timeSlot.bookingTimeSlot.startTime.substring(3, 5).toInteger()
			LocalTime endTime = LocalTime.of(hour, min, 0).plusMinutes(30)
			if(timeSlot.bookingTimeSlot.endTime.compareTo(endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString()) != 0) {
				KeywordUtil.markFailedAndStop("Interval of time slot is not 30 minutes per appointment")
			}
		}
		KeywordUtil.markPassed("Interval of time slot is 30 minutes per appointment")
	}
}
