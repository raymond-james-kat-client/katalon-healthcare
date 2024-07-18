package katalon.android.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.apache.commons.lang.RandomStringUtils

import katalon.fw.lib.Page
import katalon.services.SpecialityService

public class RandomProfileGenerator {
	public String generateRandomDescription() {
		return RandomStringUtils.randomAlphanumeric(30)
	}

	public String generateRandomSpeciality() {
		Random random = new Random();
		def specialityList = Page.nav(SpecialityService)
				.initRequestObject()
				.getAllSpeciality()
				.parseResponseBodyToJsonObject()
				.data
		def sizeOfSpeciality = ((Collection<Object>)specialityList).size()
		def randomIndex = random.nextInt(sizeOfSpeciality - 1)

		return specialityList[randomIndex].id.toString()
	}

	public Object generateRandomFullSpeciality() {
		Random random = new Random();
		def specialityList = Page.nav(SpecialityService)
				.initRequestObject()
				.getAllSpeciality()
				.parseResponseBodyToJsonObject()
				.data
		def sizeOfSpeciality = ((Collection<Object>)specialityList).size()
		def randomIndex = random.nextInt(sizeOfSpeciality - 1)

		return specialityList[randomIndex]
	}

	public String generateRandomDegree() {
		String[] degreeList = [
			"DOCTOR",
			"SPECIALIST_DOCTOR",
			"SPECIALIST_DOCTOR_GRADE_I",
			"SPECIALIST_DOCTOR_GRADE_II",
			"MASTER_OF_MEDICINE",
			"DOCTOR_OF_MEDICINE",
			"PROFESSOR_OF_MEDICINE",
			"ASSOCIATE_PROFESSOR_OF_MEDICINE"
		]

		Random random = new Random();
		def randomIndex = random.nextInt(degreeList.length);
		return degreeList[randomIndex]
	}

	public String generateRandomEmail() {
		return (RandomStringUtils.randomAlphanumeric(10) + "auto@gmail.com").toLowerCase()
	}

	public String generateRandomPassword() {
		String specialChars = "!@#%^&*()_+";
		return (RandomStringUtils.randomAlphabetic(6) +
				specialChars.charAt(RandomStringUtils.randomNumeric(1).toInteger()) +
				RandomStringUtils.randomNumeric(1)).capitalize().toString()
	}

	public String generateRandomName() {
		'Generate a random name with 2 words'
		return RandomStringUtils.randomAlphabetic(3).capitalize() +" "+ RandomStringUtils.randomAlphabetic(5).capitalize()
	}

	public String generateRandomGender() {
		def random = new Random();
		return random.nextBoolean() ? "MALE" : "FEMALE";
	}

	public String generateRandomAddress() {
		'Generate a random address with house number with 1 "/" and street name'
		return RandomStringUtils.randomNumeric(3).replaceFirst("^0+", "") + "/" + RandomStringUtils.randomNumeric(2).replaceFirst("^0+", "") + " " + RandomStringUtils.randomAlphabetic(7)
	}

	public String generateRandomPhoneNumber() {
		'Generate a random Vietnamese phone number'
		def random = new Random();
		return "+8490" + RandomStringUtils.randomNumeric(7)
	}

	public String generateRandomWeight() {
		return RandomStringUtils.randomNumeric(3).replaceFirst("^0+", "")
	}

	public String generateRandomHeight() {
		return RandomStringUtils.randomNumeric(3).replaceFirst("^0+", "")
	}

	public String generateRandomBirthday(dateFormat = "yyyy-MM-dd") {
		LocalDate today = LocalDate.now()
		Random random = new Random();
		int daysInThePast = random.nextInt(10000);
		LocalDate randomBirthday_notformatted = today.minusDays(daysInThePast);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		return randomBirthday_notformatted.format(formatter)
	}
}