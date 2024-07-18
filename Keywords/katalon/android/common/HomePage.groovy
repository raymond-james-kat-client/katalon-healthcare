package katalon.android.common

import katalon.fw.lib.BasePageMobile

public class HomePage extends BasePageMobile<HomePage>{
	public HomePage verifySignInSuccessfully(String name) {
		def greetings_xpath = "//android.view.View[@content-desc='Hello ${name}!']"
		verifyElementVisible(xpath(greetings_xpath))
		return this
	}

	public HomePage tapProfilePicture() {
		tap(xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]"), 0)
		return this
	}

	public HomePage tapEditProfileButton() {
		tap(accessibilityid("Edit profile"))
		return this
	}

	public HomePage tapSignOut() {
		tap(accessibilityid("Sign out"))
		return this
	}

	public HomePage tapPrimaryCare() {
		tap(accessibilityid("Primary Care"))
		return this
	}

	public HomePage verifyHomeButtonIsSelected() {
		verifyEqual(getAttribute(accessibilityid("Home\nTab 1 of 4"), "selected", 0), "true")
		return this
	}
}
