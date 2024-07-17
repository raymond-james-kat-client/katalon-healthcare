package katalon.services
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable
import katalon.fw.lib.BaseService
import katalon.fw.lib.Page

public class RequestReviewService extends BaseService<RequestReviewService>{

	def requestReviewUrl = GlobalVariable.apiUrl +  'request-reviews'

	public RequestReviewService verifyRequestStatus(String request_id, String status) {
		def requestList = parseResponseBodyToJsonObject().items
		for (def request : requestList) {
			if (request.id == request_id) {
				Mobile.verifyEqual(request.status, status)
				break
			}
		}
	}

	public RequestReviewService getAllMedicineRequests(String token) {
		setUrl(requestReviewUrl + '/medicines').setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public getRandomApprovedRequest() {
		def isApproved = false
		while (isApproved == false) {
			def randomRequest = getRandomRequest()
			def requestStatus = randomRequest.status
			if (requestStatus == "APPROVED") {
				isApproved = true
				return randomRequest
			}
		}
	}

	public getRandomRequest() {
		def requestList = parseResponseBodyToJsonObject().items
		def totalRequests = requestList.size()

		Random random = new Random();
		def randomIndex = random.nextInt(totalRequests-1).toInteger()

		def uuid = requestList[randomIndex].id
		def status = requestList[randomIndex].status

		Map map = new HashMap()

		map.put("randomIndex", randomIndex)
		map.put("uuid", uuid)
		map.put("status", status)

		return map
	}

	public RequestReviewService approveRequest(String token, String uuid) {
		setUrl(requestReviewUrl + '/' + uuid).setBearerAuthorizationHeader(token)
				.setPayLoad('{"status": "APPROVED"}')
				.sendPutRequest()
	}

	public RequestReviewService rejectRequest(String token, String uuid) {
		setUrl(requestReviewUrl + '/' + uuid).setBearerAuthorizationHeader(token)
				.setPayLoad('{"status": "REJECTED"}')
				.sendPutRequest()
	}
}
