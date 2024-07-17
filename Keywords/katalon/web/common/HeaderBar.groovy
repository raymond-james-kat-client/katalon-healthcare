package katalon.web.common

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import katalon.fw.lib.BasePageWeb

public class HeaderBar extends BasePageWeb<HeaderBar> {

	public HeaderBar clickAvatar() {
		WebUI.click(css("div[data-testid='user-avatar']"))
		return this
	}

	public HeaderBar clickProfile() {
		WebUI.click(css("li[data-testid='profile-button']"))
		return this
	}

	public HeaderBar clickLogout() {
		WebUI.click(css("li[data-testid='logout-button']"))
		return this
	}
}
