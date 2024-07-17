package katalon.services
import internal.GlobalVariable
import katalon.fw.lib.BaseService

public class DoctorProfileService extends BaseService<DoctorProfileService> {

	private String doctorProfileUrl = GlobalVariable.apiUrl + "doctors/my-profile"

	public DoctorProfileService updateDoctorProfile(String token, String fullName, String phoneNumber, String gender, String birthday, String address, String speciality, String degree, String description) {
		setUrl(doctorProfileUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad('{"fullName": "'+fullName+'","phoneNumber": "'+phoneNumber+'","gender": "'+gender+'","birthday": "'+birthday+'","address": "'+address+'","speciality": "'+speciality+'","degree": "'+degree+'","description": "'+description+'"}')
				.sendPutRequest()
	}

	public DoctorProfileService getDoctorProfile(String token) {
		setUrl(doctorProfileUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}
}
