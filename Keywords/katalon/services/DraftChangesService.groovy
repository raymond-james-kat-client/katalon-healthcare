package katalon.services

import internal.GlobalVariable
import katalon.fw.lib.BaseService

public class DraftChangesService extends BaseService<DraftChangesService> {

	private String draftChangesUrl = GlobalVariable.apiUrl+"doctors/draft-changes"

	public DraftChangesService getDraftChangeByDoctorId(String uuid, String token) {
		def draftChangeByIdUrl = draftChangesUrl + "/${uuid}"
		setUrl(draftChangeByIdUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}

	public DraftChangesService reviewDoctorProfile(String review, String uuid, String token) {
		def reviewProfileUrl = GlobalVariable.apiUrl + "doctors/review-profile/$uuid"
		setUrl(reviewProfileUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad('{"status": "'+review+'"}')
				.sendPutRequest()
	}

	public DraftChangesService getAllProfileDraft(String token) {
		setUrl(draftChangesUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}
}
