package katalon.services

import internal.GlobalVariable
import katalon.fw.lib.BaseService

public class DoctorManagementService extends BaseService<DoctorManagementService> {

	private String doctorsUrl = GlobalVariable.apiUrl + "doctors"

	private String getAllDoctorDraftProfileUrl = GlobalVariable.apiUrl + "doctors/draft-changes"

	public getRandomDoctor (String token) {

		def doctorList = this.initRequestObject().getAllDoctors(token)
				.parseResponseBodyToJsonObject()
				.data.doctors

		def totalDoctors = doctorList.size()

		Random random = new Random();
		def randomIndex = random.nextInt(totalDoctors-1).toInteger()

		def doctorUUID = doctorList[randomIndex].id

		Map map = new HashMap()

		map.put("randomIndex", randomIndex)
		map.put("doctorUUID", doctorUUID)

		return map
	}

	public DoctorManagementService getAllDoctors(String token) {

		setUrl(doctorsUrl).setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public DoctorManagementService updateDoctorProfile(String uuid, String fullName, String phoneNumber, String gender, String birthday, String address, String speciality, String degree, String description, String token = GlobalVariable.adminAccessToken) {

		def String updateDoctorProfileUrl = GlobalVariable.apiUrl +  "doctors/${uuid}"

		setUrl(updateDoctorProfileUrl).setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad('{"fullName": "'+fullName+'","phoneNumber": "'+phoneNumber+'","gender": "'+gender+'","birthday": "'+birthday+'","address": "'+address+'","speciality": "'+speciality+'","degree": "'+degree+'","description": "'+description+'"}')
				.sendPutRequest()
	}

	public DoctorManagementService getDoctorProfile(String token, String uuid) {

		def String getDoctorProfileUrl = GlobalVariable.apiUrl +  "doctors/${uuid}"

		setUrl(getDoctorProfileUrl).setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public DoctorManagementService createDoctor(String token, String email, String fullName, String phoneNumber, String password) {
		setUrl(doctorsUrl).setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad("""
							{
							  "email": "$email",
							  "fullName": "$fullName",
							  "phoneNumber": "$phoneNumber",
							  "password": "$password",
							  "role": "DOCTOR_ROLE"
							}""")
				.sendPostRequest()
	}

	public DoctorManagementService activateDoctor(String token, String uuid) {
		def String activateDoctorUrl = GlobalVariable.apiUrl +  "doctors/activate/${uuid}"
		setUrl(activateDoctorUrl).setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendPutRequest()
	}

	public DoctorManagementService deactivateDoctor(String token, String uuid) {
		def String deactivateDoctorUrl = GlobalVariable.apiUrl +  "doctors/deactivate/${uuid}"
		setUrl(deactivateDoctorUrl).setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendPutRequest()
	}

	public DoctorManagementService getAllDoctorDraftProfile(String token) {
		setUrl(getAllDoctorDraftProfileUrl).setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendPutRequest()
	}
}
