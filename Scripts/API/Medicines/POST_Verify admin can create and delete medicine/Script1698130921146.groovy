import internal.GlobalVariable
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.MedicinesService
import katalon.services.SignInService
import katalon.util.APIUtil
import katalon.util.MedicineUtil

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

def adminToken = Page.nav(SignInService)
						.initRequestObject()
						.signIn(user.email, user.pwd)
						.getAccessToken()
						
'Generate random medicine information to put in the api'
 def medicineInfo = Page.nav(MedicineUtil).generateRandomMedicineInformation()

'Call CREATE api'
def payLoad = Page.nav(APIUtil).constructPayLoadFromMap(medicineInfo)
	
def responseCreate = Page.nav(MedicinesService)
				.initRequestObject()
				.createMedicine(adminToken, payLoad)
	
'Verify the response'
def apiMedicineCreate = responseCreate.parseResponseBodyToJsonObject().data
				
responseCreate.verifyStatusCode(200)
		.verifyPropertyValue("message", "Create medicine successfully")
		.verifyCorrectMedicine(apiMedicineCreate, medicineInfo)
		
'Verify getting medicine details successfully'
def medicineId = responseCreate.getPropertyValue("data.id")

def responseGet = Page.nav(MedicinesService)
						.initRequestObject()
						.getMedicineDetails(adminToken, medicineId)
						
def apiMedicineGet = responseGet.parseResponseBodyToJsonObject()

responseGet.verifyCorrectMedicine(apiMedicineGet, medicineInfo)
		
'Verify delete medicine'
Page.nav(MedicinesService)
		.initRequestObject()
		.deleteMedicine(adminToken, medicineId)
		.verifyStatusCode(204)