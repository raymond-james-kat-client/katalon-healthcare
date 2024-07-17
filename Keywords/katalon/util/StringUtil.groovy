package katalon.util

import org.apache.commons.lang.RandomStringUtils


public class StringUtil {

	public String generateRandomUUID() {
		return UUID.randomUUID().toString()
	}

	public Integer generateRandomNumber(Integer minNumber=0, Integer maxNumber=10000) {
		Random random = new Random()
		return random.nextInt(maxNumber) + minNumber
	}

	public String generateRandomAlphabeticString(Integer num_words = generateRandomNumber(1, 20)) {

		def randomString = ""

		for (def i=0; i<num_words; i++) {
			'Limit word length to 7'
			Random random = new Random()
			def length = random.nextInt(7) + 1
			def randomWord = RandomStringUtils.randomAlphabetic(length)
			randomString += randomWord + " "
		}

		return randomString
	}
}
