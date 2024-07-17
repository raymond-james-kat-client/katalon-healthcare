package katalon.util

import org.apache.commons.lang.RandomStringUtils

import katalon.fw.lib.Page

public class MedicineUtil {
	def List<String> dosageForm = [
		"TABLET",
		"CAPSULE",
		"SYRUP",
		"INJECTION",
		"CREAM",
		"OINTMENT",
		"DROPS",
		"INHALER",
		"PATCH",
		"SUPPOSITORY",
		"LOZENGE",
		"GEL",
		"POWDER",
		"LOTION",
		"MOUTHWASH",
		"DROPS_FOR_INHALATION",
		"SHAMPOO",
		"SOLUTION",
		"EMULSION",
		"AEROSOL",
		"OTHERS"
	]

	def List<String> aministrationRoute = [
		"ORAL",
		"INTRAVENOUS",
		"INTRAMUSCULAR",
		"SUBCUTANEOUS",
		"TOPICAL",
		"OPHTHALMIC",
		"OTIC",
		"NASAL",
		"RECTAL",
		"VAGINAL",
		"INHALATION",
		"TRANSDERMAL",
		"INTRADERMAL",
		"ENDOTRACHEAL",
		"INTRA_ARTICULAR",
		"INTRATHECAL",
		"INTRAOCULAR",
		"EPIDURAL",
		"PERITONEAL",
		"INTRACARDIAC",
		"OTHERS"
	]

	def List<String> unit = [
		"BOX",
		"BOTTLE",
		"PACK",
		"PILL"
	]

	def String generateRandomDosageForm() {
		Random random = new Random()
		def randomIndex = random.nextInt(dosageForm.size()-1)

		return dosageForm[randomIndex]
	}

	def String generateRandomAdministrationRoute() {
		Random random = new Random()
		def randomIndex = random.nextInt(aministrationRoute.size()-1)

		return aministrationRoute[randomIndex]
	}

	def String generateRandomUnit() {
		Random random = new Random()
		def randomIndex = random.nextInt(unit.size()-1)

		return unit[randomIndex]
	}

	def String generateRandomPrice() {
		Random random = new Random()
		def length = random.nextInt(4)+1

		return RandomStringUtils.randomNumeric(length)
	}

	def Map constructMedicineMap(String name, String genericName, String brandName, String dosageForm,
			String strength, String administrationRoute, String instruction, String interaction, String precaution, String sideEffect,
			String storageRequirement, String unit, String price) {

		HashMap<String, String> map = new HashMap<String, String>()
		map.put("name", name)
		map.put("genericName", genericName)
		map.put("brandName", brandName)
		map.put("dosageForm", dosageForm)
		map.put("strength", strength)
		map.put("administrationRoute", administrationRoute)
		map.put("instruction", instruction)
		map.put("interaction", interaction)
		map.put("precaution", precaution)
		map.put("sideEffect", sideEffect)
		map.put("storageRequirement", storageRequirement)
		map.put("unit", unit)
		map.put("price", price)

		return map
	}

	def Map generateRandomMedicineInformation() {
		def name = Page.nav(StringUtil).generateRandomAlphabeticString()
		def genericName = Page.nav(StringUtil).generateRandomAlphabeticString()
		def brandName = Page.nav(StringUtil).generateRandomAlphabeticString()
		def dosageForm = generateRandomDosageForm()
		def strength = Page.nav(StringUtil).generateRandomAlphabeticString()
		def administrationRoute = generateRandomAdministrationRoute()
		def instruction = Page.nav(StringUtil).generateRandomAlphabeticString()
		def interaction = Page.nav(StringUtil).generateRandomAlphabeticString()
		def precaution = Page.nav(StringUtil).generateRandomAlphabeticString()
		def sideEffect = Page.nav(StringUtil).generateRandomAlphabeticString()
		def storageRequirement = Page.nav(StringUtil).generateRandomAlphabeticString()
		def unit = generateRandomUnit()
		def price =generateRandomPrice()

		return constructMedicineMap(name, genericName, brandName, dosageForm, strength,
				administrationRoute, instruction, interaction, precaution, sideEffect,
				storageRequirement, unit, price)
	}

	def Map generateRandomInformationForUpdate(Map map = new HashMap<String, String>()) {
		def instruction = Page.nav(StringUtil).generateRandomAlphabeticString()
		def interaction = Page.nav(StringUtil).generateRandomAlphabeticString()
		def precaution = Page.nav(StringUtil).generateRandomAlphabeticString()
		def sideEffect = Page.nav(StringUtil).generateRandomAlphabeticString()
		def storageRequirement = Page.nav(StringUtil).generateRandomAlphabeticString()

		map.put("instruction", instruction)
		map.put("interaction", interaction)
		map.put("precaution", precaution)
		map.put("sideEffect", sideEffect)
		map.put("storageRequirement", storageRequirement)

		return map
	}
}
