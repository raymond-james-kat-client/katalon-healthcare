package katalon.web.admin

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import katalon.fw.lib.BasePageWeb

public class LeftNavBar extends BasePageWeb<LeftNavBar> {
	public LeftNavBar clickSpecialty() {
		WebUI.click(text("Specialties"))
		return this
	}

	public LeftNavBar clickDoctor() {
		WebUI.click(text("Doctors"))
		return this
	}

	public LeftNavBar clickPatients() {
		WebUI.click(text("Patients"))
		return this
	}
}
