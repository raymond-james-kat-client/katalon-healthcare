package katalon.services
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable
import katalon.fw.lib.BaseService

public class MedicinesService extends BaseService<MedicinesService> {

	String medicinesUrl = GlobalVariable.apiUrl + 'medicines'
	String requestCreateUrl = GlobalVariable.apiUrl + 'medicines/request-create'

	public MedicinesService updateMedicineDetails(String token, String medicineId, String payLoad) {
		def updateMedicineUrl = medicinesUrl + "/${medicineId}"
		setUrl(updateMedicineUrl).setBearerAuthorizationHeader(token)
				.setPayLoad(payLoad)
				.sendPutRequest()
	}

	public MedicinesService requestUpdateMedicine(String token, String id, String payLoad) {
		def requestUpdateUrl = GlobalVariable.apiUrl + "medicines/request-update/${id}"
		setUrl(requestUpdateUrl).setBearerAuthorizationHeader(token)
				.setPayLoad(payLoad)
				.sendPutRequest()
	}

	public MedicinesService requestCreateMedicine(String token, String payLoad) {
		setUrl(requestCreateUrl).setBearerAuthorizationHeader(token)
				.setPayLoad(payLoad)
				.sendPostRequest()
	}

	public MedicinesService deleteMedicine(String token, String medicine_id) {
		def deleteMedicine = medicinesUrl + "/${medicine_id}"

		setUrl(deleteMedicine).setBearerAuthorizationHeader(token)
				.sendDeleteRequest()
	}

	public MedicinesService createMedicine(String token, String payLoad) {
		setUrl(medicinesUrl).setBearerAuthorizationHeader(token)
				.setPayLoad(payLoad)
				.sendPostRequest()
	}

	public MedicinesService getMedicineDetails(String token, String medicine_id) {

		def medicineDetailUrl = medicinesUrl + "/${medicine_id}"

		setUrl(medicineDetailUrl).setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public MedicinesService getAllMedicines(String token, String page = 1, String itemPerPage = 10, String sortBy = "ID", String order = "DESC") {
		List<TestObjectProperty> parameters = new ArrayList<>()
		parameters.add(new TestObjectProperty('page', ConditionType.EQUALS, page))
		parameters.add(new TestObjectProperty('itemPerPage', ConditionType.EQUALS, itemPerPage))
		parameters.add(new TestObjectProperty('sortBy', ConditionType.EQUALS, sortBy))
		parameters.add(new TestObjectProperty('order', ConditionType.EQUALS, order))
		setUrl(medicinesUrl).setBearerAuthorizationHeader(token)
				.setParam(parameters)
				.sendGetRequest()
	}

	public getRandomMedicine() {
		def medicineList = parseResponseBodyToJsonObject().items
		def totalMedicines = medicineList.size()
		Random random = new Random()
		def randomIndex = random.nextInt(totalMedicines-1)

		def medicineUUID = medicineList[randomIndex].id

		Map map = new HashMap()

		map.put("randomIndex", randomIndex)
		map.put("medicineUUID", medicineUUID)

		return map
	}

	public MedicinesService verifyCorrectMedicine(Map apiResponse, Map correctInfo) {
		for (def key : correctInfo.keySet()) {
			Mobile.verifyEqual(apiResponse[key], correctInfo[key])
		}
		return this
	}
}
