package katalon.fw.lib

import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.util.CryptoUtil

import internal.GlobalVariable

public class Credential {
	String email
	private String encryptedEmail
	String pwd
	String fullName
	String role
	String speciality
	String degree
	String gender
	String address
	String height
	String weight
	String phoneNumber
	String birthday
	String description
	private List<Credential> filteredCredentials = []

	public Credential() {
	}

	public Credential getCredentials() {
		TestData testData = GlobalVariable.credentialDataFile
		int rowNumbers = testData.getRowNumbers()
		List<Credential> credentials = []
		if (rowNumbers > 0) {
			(1..rowNumbers).each({ rowNumber ->
				credentials.add(new Credential().with {
					encryptedEmail = testData.getValue('email', rowNumber)
					email = CryptoUtil.decode(CryptoUtil.getDefault(encryptedEmail))
					pwd = testData.getValue('pwd', rowNumber)
					fullName = testData.getValue('fullName', rowNumber)
					speciality = testData.getValue('speciality', rowNumber)
					degree = testData.getValue('degree', rowNumber)
					gender = testData.getValue('gender', rowNumber)
					role = testData.getValue('role', rowNumber)
					address = testData.getValue('address', rowNumber)
					height = testData.getValue('height', rowNumber)
					weight = testData.getValue('weight', rowNumber)
					phoneNumber = testData.getValue('phoneNumber', rowNumber)
					birthday = testData.getValue('birthday', rowNumber)
					description = testData.getValue('description', rowNumber)
					it
				})
			})
		}
		filteredCredentials = credentials
		return this
	}

	public Credential withRole(String role) {
		filteredCredentials = filteredCredentials.findAll {
			it.role.toLowerCase() == role.toLowerCase()
		}
		return this
	}

	public Credential withEmailEqual(String expected) {
		if (!expected.isEmpty()) {
			filteredCredentials = filteredCredentials.findAll {
				it.email == expected
			}
		}
		return this
	}

	public Credential withFullName(String fullName) {
		filteredCredentials = filteredCredentials.findAll {
			it.fullName.toLowerCase() == fullName.toLowerCase()
		}
		return this
	}

	public Credential inSpeciality(String specialityName) {
		if (!specialityName.isEmpty()) {
			filteredCredentials = filteredCredentials.findAll {
				it.speciality == specialityName
			}
		}
		return this
	}

	public Credential withDegree(String degreeName) {
		filteredCredentials = filteredCredentials.findAll {
			it.degree == degreeName
		}
		return this
	}

	public Credential withGender(String genderName) {
		if (!genderName.isEmpty()) {
			filteredCredentials = filteredCredentials.findAll {
				it.gender == genderName
			}
		}
		return this
	}

	public Credential getFirst() {
		return filteredCredentials.first()
	}

	public Credential getLast() {
		return filteredCredentials.last()
	}
}