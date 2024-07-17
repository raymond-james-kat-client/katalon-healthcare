import katalon.android.common.RandomProfileGenerator
import katalon.fw.lib.Credential
import katalon.fw.lib.Page
import katalon.services.DoctorManagementService
import katalon.services.DoctorProfileService
import katalon.services.DraftChangesService
import katalon.services.SignInService

'Get account to log in'
Credential user = Page.nav(Credential)
	.getCredentials()
	.withRole("Admin")
	.getFirst()

'Log in as admin'
def adminToken = Page.nav(SignInService)
					.initRequestObject()
					.signIn(user.email, user.pwd)
					.getAccessToken()
	
'Generate random information to create doctor'
def email = Page.nav(RandomProfileGenerator).generateRandomEmail()
def fullName = Page.nav(RandomProfileGenerator).generateRandomName()
def phoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def password = "P@ssw0rd@auto"
	
'Create a new doctor to not affect other tc'
Page.nav(DoctorManagementService)
	.initRequestObject()
	.createDoctor(adminToken, email, fullName, phoneNumber, password)

'Log in as doctor to request change profile'
user = Page.nav(Credential)
	.getCredentials()
	.withRole("Doctor")
	.getFirst()
def doctorToken = Page.nav(SignInService)
						.initRequestObject()
						.signIn(user.email, user.pwd)
						.getAccessToken()
	
'Generate random information to update doctor profile'
def newFullName = Page.nav(RandomProfileGenerator).generateRandomName()
def newPhoneNumber = Page.nav(RandomProfileGenerator).generateRandomPhoneNumber()
def gender = Page.nav(RandomProfileGenerator).generateRandomGender()
def address = Page.nav(RandomProfileGenerator).generateRandomAddress()
def birthday = Page.nav(RandomProfileGenerator).generateRandomBirthday()
def speciality = Page.nav(RandomProfileGenerator).generateRandomSpeciality()
def degree = Page.nav(RandomProfileGenerator).generateRandomDegree()
def description = Page.nav(RandomProfileGenerator).generateRandomDescription()
def photo = "photoAuto"

'Doctor request change profile and get doctor id'
def doctorId = Page.nav(DoctorProfileService)
	.initRequestObject()
	.updateDoctorProfile(doctorToken, newFullName, newPhoneNumber, gender, birthday, address, speciality, degree, description)
	.parseResponseBodyToJsonObject()
	.data.id
	
'Review (Approve or Reject)'
Page.nav(DraftChangesService)
	.initRequestObject()
	.reviewDoctorProfile(review, doctorId, adminToken)
	.verifyStatusCode(200)
	.verifyPropertyValue("message", "Review sent successfully")