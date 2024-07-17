package katalon.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import katalon.fw.lib.Page



public class DateUtil {

	public String generateDateInFuture(int minDays = 0, int maxDays = 1000) {
		LocalDate today = LocalDate.now()
		Random random = new Random();
		int daysInTheFuture = random.nextInt(maxDays - minDays) + minDays;
		LocalDate randomBirthday_notformatted = today.plusDays(daysInTheFuture);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return randomBirthday_notformatted.format(formatter)
	}

	public String generateDateInPast(int minDays = 0, int maxDays = 1000) {
		LocalDate today = LocalDate.now()
		Random random = new Random();
		int daysInThePast = random.nextInt(maxDays - minDays) + minDays;
		LocalDate randomBirthday_notformatted = today.minusDays(daysInThePast);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return randomBirthday_notformatted.format(formatter)
	}
	
	public String changeDateFormat(String dateString, String format) {
		List<String> dateSplit = dateString.split("-")
		LocalDate date = LocalDate.of(dateSplit[0].toInteger(), dateSplit[1].toInteger(), dateSplit[2].toInteger())

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return date.format(formatter)
	}

	public getNext14Days() {

		List<String> datesList = new ArrayList<>();

		for (int i=1; i<15; i++) {
			String date = generateDateInFuture(i,i+1)
			datesList.add(date)
		}

		return  datesList
	}
}
