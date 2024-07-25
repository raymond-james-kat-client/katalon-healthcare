import internal.GlobalVariable
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
def medicineId = Page.nav(MedicinesService)
							.initRequestObject()
							.getAllMedicines(doctorToken)
							.getRandomMedicine()['medicineUUID']

def medicineInfo = Page.nav(MedicineUtil).generateRandomInformationForUpdate()
def payLoad = Page.nav(APIUtil).constructPayLoadFromMap(medicineInfo)

def requestId = Page.nav(MedicinesService)
					.initRequestObject()
					.requestUpdateMedicine(doctorToken, medicineId, payLoad)
					.verifyStatusCode(200)
					.parseResponseBodyToJsonObject()
					.data.id
		
'Approve the request'
user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

def adminToken = Page.nav(SignInService)
						.initRequestObject()
						.signIn(user.email, user.pwd)
						.getAccessToken()
						
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

'Delete the created medicine'
Page.nav(MedicinesService)
	.initRequestObject()
	.deleteMedicine(adminToken, medicineId)
	
	
	