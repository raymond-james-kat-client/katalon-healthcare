package katalon.services

import katalon.fw.lib.BaseService
import internal.GlobalVariable

public class PatientManagementService extends BaseService<PatientManagementService> {
	String patientManagementUrl = GlobalVariable.apiUrl+"admin/patients"

	public PatientManagementService getPatientList(String token) {
		setUrl(patientManagementUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public PatientManagementService getPatientInfo(String token, String uuid) {
		setUrl(patientManagementUrl+"/"+uuid)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public PatientManagementService updatePatient(String token, String fullName, String address, String birthday,
			String phoneNumber, String gender, Number weight, Number height) {
		setUrl(patientManagementUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad('{"fullName": "'+fullName+'", "address": "'+address+'", "birthday": "'+birthday+'", "phoneNumber": "'+phoneNumber+'", "gender": "'+gender+'", "weight": "'+weight+'", "height": "'+height+'"}')
				.sendPutRequest()
	}
}
