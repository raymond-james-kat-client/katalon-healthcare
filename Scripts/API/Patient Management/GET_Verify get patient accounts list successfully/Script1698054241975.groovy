import internal.GlobalVariable
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.PatientManagementService
import katalon.services.SignInService

'Get account to login'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

'Sign in as admin and get token'
String adminAccessToken = Page.nav(SignInService).initRequestObject()
												.signIn(user.email, user.pwd)
												.getAccessToken()
'Verify get patient accounts list successfully'												
Page.nav(PatientManagementService).initRequestObject()
									.getPatientList(adminAccessToken)
									.verifyStatusCode(200)
									.validateJsonAgainstSchema("""
										{
										"\$schema": "http://json-schema.org/draft-04/schema#",
										"type": "object",
										"properties": {
										  "items": {
											"type": "array",
											"items": [
											  {
												"type": "object",
												"properties": {
												  "id": {
													"type": "string"
												  },
												  "fullName": {
													"type": "string"
												  },
												  "address": {
													"type": "string"
												  },
												  "email": {
													"type": "string"
												  },
												  "birthday": {
													"type": "string"
												  },
												  "phoneNumber": {
													"type": "string"
												  },
												  "gender": {
													"type": "string"
												  },
												  "weight": {
													"type": "integer"
												  },
												  "height": {
													"type": "integer"
												  },
												  "activated": {
													"type": "boolean"
												  }
												},
												"required": [
												  "id",
												  "fullName",
												  "address",
												  "email",
												  "phoneNumber",
												  "activated"
												]
											  }
											]
										  },
										  "pagination": {
											"type": "object",
											"properties": {
											  "currentPage": {
												"type": "integer"
											  },
											  "totalPages": {
												"type": "integer"
											  },
											  "totalRecords": {
												"type": "integer"
											  }
											},
											"required": [
											  "currentPage",
											  "totalPages",
											  "totalRecords"
											]
										  }
										},
										"required": [
										  "items",
										  "pagination"
										]
									  }
									}""")