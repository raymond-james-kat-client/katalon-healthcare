import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.AccountService
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
						
def accountData = Page.nav(AccountService)
					.initRequestObject()
					.getAccountData(doctorToken)
					.parseResponseBodyToJsonObject()
					.data
					
def accountId = accountData.id
def accountName = accountData.fullName
def accountRole = accountData.role

'Get medicine id'
def medicineId = Page.nav(MedicinesService)
							.initRequestObject()
							.getAllMedicines(doctorToken)
							.getRandomMedicine()['medicineUUID']

'Generate random inforamtion'
def medicineInfo = Page.nav(MedicineUtil).generateRandomInformationForUpdate()
def payLoad = Page.nav(APIUtil).constructPayLoadFromMap(medicineInfo)

'Call the api'
def response = Page.nav(MedicinesService)
	.initRequestObject()
	.requestUpdateMedicine(doctorToken, medicineId, payLoad)
	
'Verify correct response'
def apiMedicine = response.parseResponseBodyToJsonObject().data.request
response.verifyStatusCode(200)
		.verifyPropertyValue("message", "A request for medicine review is sent to the administrator")
		.verifyPropertyValue("data.type", "MEDICINE_UPDATE")
		.verifyPropertyValue("data.status", "PENDING")
		.verifyCorrectMedicine(apiMedicine, medicineInfo)
		.verifyPropertyValue("data.accountId", accountId)
		.verifyPropertyValue("data.accountName", accountName)
		.verifyPropertyValue("data.accountRole", accountRole)
		
'Reject the request so that it does not appear on UI'
user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

def requestId = response.parseResponseBodyToJsonObject().data.id
def adminToken = Page.nav(SignInService)
						.initRequestObject()
						.signIn(user.email, user.pwd)
						.getAccessToken()
						
Page.nav(RequestReviewService)
	.initRequestObject()
	.rejectRequest(adminToken, requestId)
	.verifyStatusCode(200)