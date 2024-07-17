package katalon.services

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import internal.GlobalVariable
import katalon.fw.lib.BaseService
import katalon.fw.lib.Page


public class SpecialtyManagementService extends BaseService<SpecialtyManagementService> {
	private String specialtyUrl = GlobalVariable.apiUrl+"admin/specialists"
	def specialtyList

	protected void getSpecialtyList(String token) {
		'Get specialty list'
		String totalRecords = initRequestObject()
				.getAllSpecialties(token)
				.parseResponseBodyToJsonObject().pagination.totalRecords
		this.specialtyList = initRequestObject()
				.getAllSpecialties(token, "1", totalRecords)
				.parseResponseBodyToJsonObject().items
	}

	public Object getSpecialtyByName(String token, String specialtyName) {
		'Get specialty list'
		getSpecialtyList(token)

		for (specialty in this.specialtyList) {
			if(specialtyName.compareTo(specialty.name) == 0)
				return specialty
		}

		return null
	}

	public Number getRandomIndex(String token) {
		'Get specialty list'
		getSpecialtyList(token)

		'Get random index from that specialty list'
		Random random = new Random();
		def sizeOfSpeciality = ((Collection<Object>)this.specialtyList).size()
		def randomIndex = random.nextInt(sizeOfSpeciality - 1)
		return randomIndex
	}

	public Number getAnotherIndex(String token, Number index) {
		Number anotherIndex
		Boolean isContinuing = true
		while (isContinuing) {
			Number newIndex = Page.nav(SpecialtyManagementService).getRandomIndex(token)
			if (newIndex != index) {
				isContinuing = false
				anotherIndex = newIndex
			}
		}
		return anotherIndex
	}

	public String getSpecialtyId(Number randomIndex) {
		String specialtyId = this.specialtyList[randomIndex].id.toString()
		return specialtyId
	}

	public String getSpecialtyName(Number randomIndex) {
		String specialtyName = this.specialtyList[randomIndex].name.toString()
		return specialtyName
	}

	public String getSpecialtyDescription(Number randomIndex) {
		String specialtyDescription = this.specialtyList[randomIndex].description.toString()
		return specialtyDescription
	}

	public String getSpecialtyStatus(Number randomIndex) {
		String specialtyStatus = this.specialtyList[randomIndex].activated.toString()
		return specialtyStatus
	}

	public SpecialtyManagementService getAllSpecialties(String token, String page = "1", String itemPerPage = "10", String sortBy = "ID", String order = "ASC") {
		List<TestObjectProperty> parameters = new ArrayList<>()

		parameters.add(new TestObjectProperty('page', ConditionType.EQUALS, page))
		parameters.add(new TestObjectProperty('itemPerPage', ConditionType.EQUALS, itemPerPage))
		parameters.add(new TestObjectProperty('sortBy', ConditionType.EQUALS, sortBy))
		parameters.add(new TestObjectProperty('order', ConditionType.EQUALS, order))

		setUrl(specialtyUrl)
				.setBearerAuthorizationHeader(token)
				.setParam(parameters)
				.sendGetRequest()
		return this
	}

	public SpecialtyManagementService createSpecialty(String token, String specialtyName, String description) {
		setUrl(specialtyUrl)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad('{"name": "'+specialtyName+'","description": "'+description+'"}')
				.sendPostRequest()
		return this
	}

	public Number getUnactivatedSpecialtyIndex(String token) {
		Page.nav(SpecialtyManagementService).getSpecialtyList()
		Boolean isSpecialtyActivated = true
		while (isSpecialtyActivated) {
			Number randomIndex = Page.nav(SpecialtyManagementService).getRandomIndex(token)
			String specialtyName = Page.nav(SpecialtyManagementService).getSpecialtyName(randomIndex)
			String specialtyStatus = Page.nav(SpecialtyManagementService).getSpecialtyStatus(randomIndex)
			if(specialtyStatus == "false") {
				isSpecialtyActivated = false
				return randomIndex
			}
		}
	}

	public SpecialtyManagementService activateSpecialty(String token, String specialtyId) {
		def body = ["activated": true]
		setUrl(specialtyUrl+"/"+specialtyId)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad(body)
				.sendPutRequest()
		return this
	}

	public Number getActivatedSpecialtyIndex(String token) {
		Page.nav(SpecialtyManagementService).getSpecialtyList()
		Boolean isSpecialtyUnactivated = true
		while (isSpecialtyUnactivated) {
			Number randomIndex = Page.nav(SpecialtyManagementService).getRandomIndex(token)
			String specialtyName = Page.nav(SpecialtyManagementService).getSpecialtyName(randomIndex)
			String specialtyStatus = Page.nav(SpecialtyManagementService).getSpecialtyStatus(randomIndex)
			if(specialtyStatus == "true") {
				isSpecialtyUnactivated = false
				return randomIndex
			}
		}
	}

	public SpecialtyManagementService deactivateSpecialty(String token, String specialtyId) {
		def body = ["activated": false]
		setUrl(specialtyUrl+"/"+specialtyId)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad(body)
				.sendPutRequest()
		return this
	}

	public SpecialtyManagementService updateSpecialty(String token, String randomSpecialtyUUID, String specialtyTitle, String description) {
		setUrl(specialtyUrl+"/"+randomSpecialtyUUID)
				.setJsonContentTypeHeader()
				.setBearerAuthorizationHeader(token)
				.setPayLoad('{"name": "'+specialtyTitle+'", "description": "'+description+'"}')
				.sendPutRequest()
		return this
	}

	public Number getSpecialtyIndexByName(String adminAccessToken, String name) {
		'Get specialty list'
		Page.nav(SpecialtyManagementService).getSpecialtyList(adminAccessToken)

		'Get specialty index'
		Number sizeOfSpecialtyList = ((Collection<Object>)this.specialtyList).size()
		for (Number index : (0..(sizeOfSpecialtyList-1))) {
			if(name == Page.nav(SpecialtyManagementService).getSpecialtyName(index)) {
				return index
				break
			}
		}
	}
}
