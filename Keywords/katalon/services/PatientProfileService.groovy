package katalon.services

import internal.GlobalVariable
import katalon.fw.lib.BaseService

import com.kms.katalon.core.util.KeywordUtil

public class PatientProfileService extends BaseService<PatientProfileService>{
	private String patientProfileUrl = GlobalVariable.apiUrl+"patient/my-profile"

	public PatientProfileService updatePatientProfile(String token, String fullName, String address, String birthday, String phoneNumber, String gender, String weight, String height) {
		setUrl(patientProfileUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad('{"fullName": "'+fullName+'","address": "'+address+'","birthday": "'+birthday+'","phoneNumber": "'+phoneNumber+'","gender": "'+gender+'","weight": '+weight+',"height": '+height+'}')
				.sendPutRequest()
	}

	public PatientProfileService getPatientProfile(String token) {
		setUrl(patientProfileUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}
}
