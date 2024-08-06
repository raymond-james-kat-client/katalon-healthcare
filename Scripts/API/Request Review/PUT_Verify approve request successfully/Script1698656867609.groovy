import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown

import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.MedicinesService
import katalon.services.RequestReviewService
import katalon.services.SignInService
import katalon.util.APIUtil
import katalon.util.MedicineUtil

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Doctor")
	.getFirst()

def doctorToken = Page.nav(SignInService)
	.initRequestObject()
	.signIn(user.email, user.pwd)
	.getAccessToken()

'Create a request'			
def medicineInfo = Page.nav(MedicineUtil).generateRandomInformationForUpdate()
def payLoad = Page.nav(APIUtil).constructPayLoadFromMap(medicineInfo)

def requestId = Page.nav(MedicinesService)
	.initRequestObject()
	.requestUpdateMedicine(doctorToken, medicineId, payLoad)
	.verifyStatusCode(200)
	.parseResponseBodyToJsonObject()
	.data.id
		
'Approve the request'
Page.nav(RequestReviewService)
	.initRequestObject()
	.approveRequest(adminToken, requestId)
	.verifyStatusCode(200)
	.verifyPropertyValue("message", "Review sent successfully")
	
'Verify the request status is approved and the medicine appears in medicine list'
Page.nav(RequestReviewService)
	.initRequestObject()
	.getAllMedicineRequests(adminToken)
	.verifyRequestStatus(requestId, "APPROVED")
	
def response = Page.nav(MedicinesService)
	.initRequestObject()
	.getMedicineDetails(adminToken, medicineId)
	
def apiMedicine = response.parseResponseBodyToJsonObject()
Page.nav(MedicinesService).verifyCorrectMedicine(apiMedicine, medicineInfo)

@SetUp
def setUp() {
	Credential user = Page.nav(Credential)
		.getCredentials()
		.withRole("Admin")
		.getFirst()
	
	adminToken = Page.nav(SignInService)
		.initRequestObject()
		.signIn(user.email, user.pwd)
		.getAccessToken()
	
	'Generate random medicine information to put in the api'
	def medicineInfo = Page.nav(MedicineUtil).generateRandomMedicineInformation()
   
	'Call CREATE api'
	def payLoad = Page.nav(APIUtil).constructPayLoadFromMap(medicineInfo)
	   
	medicineId = Page.nav(MedicinesService)
		.initRequestObject()
		.createMedicine(adminToken, payLoad)
		.getPropertyValue("data.id")
}

@TearDown
def tearDown() {
	'Delete the created medicine'
	Page.nav(MedicinesService)
		.initRequestObject()
		.deleteMedicine(adminToken, medicineId)
}