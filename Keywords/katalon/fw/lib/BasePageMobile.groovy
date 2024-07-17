package katalon.fw.lib
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement

class BasePageMobile<T> extends BaseElementMobile {
	def AppiumDriver<MobileElement> getCurrentSessionMobileDriver() {
		return MobileDriverFactory.getDriver();
	}

	protected def T sendKeys(TestObject to, String text) {
		MobileBuiltInKeywords.sendKeys(to, text)
		return this
	}

	protected def T setText(TestObject to, String text, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.setText(to, text, timeout)
		return this
	}

	protected def T setEncryptedText(TestObject to, String text, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.setEncryptedText(to, text, timeout)
		return this
	}

	protected def T clearText(TestObject to, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.clearText(to, timeout)
		return this
	}

	protected def getText(TestObject to, int timeout = GlobalVariable.timeOut) {
		return MobileBuiltInKeywords.getText(to, timeout)
	}

	protected def getAttribute(TestObject to, String attribute, int timeout = GlobalVariable.timeOut) {
		return MobileBuiltInKeywords.getAttribute(to, attribute, timeout)
	}

	protected def getElementWidth(TestObject to, int timeout = GlobalVariable.timeOut) {
		return MobileElementCommonHelper.getElementWidth(to, timeout)
	}

	protected def getElementHeight(TestObject to, int timeout = GlobalVariable.timeOut) {
		return MobileElementCommonHelper.getElementHeight(to, timeout)
	}

	protected def T tap(TestObject to, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.tap(to, timeout)
		return this
	}

	protected def T tapAtPosition(Number x, Number y) {
		MobileElementCommonHelper.tapAtPosition(x, y)
		return this
	}

	protected def T tapAndHold(Number x, Number y, Number duration){
		MobileElementCommonHelper.tapAndHold(x, y, duration)
		return this
	}

	protected def T tapAndHold(TestObject to, Number duration, int timeout = GlobalVariable.timeOut) {
		MobileElementCommonHelper.tapAndHold(to, duration, timeout)
		return this
	}

	protected def T doubleTap(TestObject to, Number duration, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.doubleTap(to, duration, timeout)
		return this
	}

	protected def T pressBack() {
		MobileBuiltInKeywords.pressBack()
		return this
	}

	protected def T swipe(int startX, int startY, int endX, int endY) {
		MobileBuiltInKeywords.swipe(startX, startY, endX, endY)
		return this
	}

	protected def T hideKeyboard() {
		MobileBuiltInKeywords.hideKeyboard()
		return this
	}

	protected def T takeScreenshot(String fileName) {
		MobileBuiltInKeywords.takeScreenshot(fileName)
		return this
	}

	protected def T checkElement(TestObject to, int timeout = GlobalVariable.timeOut) {
		MobileElementCommonHelper.checkElement(to, timeout)
		return this
	}

	protected def T uncheckElement(TestObject to, int timeout = GlobalVariable.timeOut) {
		MobileElementCommonHelper.uncheckElement(to, timeout)
		return this
	}

	protected def T selectListItemByLabel(TestObject to, String label, int timeout = GlobalVariable.timeOut, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileElementCommonHelper.selectListItemByLabel(to, label, timeout, flowControl)
		return this
	}

	protected def T selectItemByIndex(TestObject to, int index, int timeout = GlobalVariable.timeOut, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileElementCommonHelper.selectItemByIndex(to, index, timeout, flowControl)
		return this
	}

	protected def T dragAndDrop(TestObject fromObj, TestObject toObj, int timeout = GlobalVariable.timeOut) {
		MobileElementCommonHelper.dragAndDrop(fromObj, toObj, timeout)
		return this
	}

	protected def T moveSlider(TestObject to, Number percent, int timeout = GlobalVariable.timeOut) {
		MobileElementCommonHelper.moveSlider(to, percent, timeout)
		return this
	}

	protected def T scrollToText(String text, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.scrollToText(text, flowControl)
		return this
	}

	protected def T switchToLandscape() {
		MobileBuiltInKeywords.switchToLandscape()
		return this
	}

	protected def T switchToNative() {
		MobileBuiltInKeywords.switchToNative()
		return this
	}

	protected def T switchToPortrait() {
		MobileBuiltInKeywords.switchToPortrait()
		return this
	}

	protected def T switchToWebView() {
		MobileBuiltInKeywords.switchToWebView()()
		return this
	}

	protected def T waitForElementPresent(TestObject to, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.waitForElementPresent(to, timeout)
		return this
	}

	protected def T waitForElementNotPresent(TestObject to, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.waitForElementNotPresent(to, timeout)
		return this
	}

	protected def T waitForElementHasAttribute(TestObject to, String attributeName, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.waitForElementHasAttribute(to, attributeName, timeout)
		return this
	}

	protected def T waitForElementNotHasAttribute(TestObject to, String attributeName, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.waitForElementNotHasAttribute(to, attributeName, timeout)
		return this
	}

	protected def T waitForElementAttributeValue(TestObject to, String attributeName, String attributeValue, int timeout = GlobalVariable.timeOut) {
		MobileBuiltInKeywords.waitForElementAttributeValue(to, attributeName, attributeValue, timeout)
		return this
	}

	protected def checkDuration(Number duration) {
		MobileElementCommonHelper.checkDuration(duration)
	}

	protected def isDurationInvalid(Number duration) {
		MobileElementCommonHelper.isDurationInvalid(duration)
	}

	protected def isElementChecked(TestObject to, int timeout = GlobalVariable.timeOut) {
		MobileElementCommonHelper.isElementChecked(to, timeout)
	}

	protected def verifyElementVisible(TestObject to, int timeout = GlobalVariable.timeOut, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.verifyElementVisible(to, timeout, flowControl)
	}

	protected def verifyElementNotVisible(TestObject to, int timeout = GlobalVariable.timeOut, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.verifyElementNotVisible(to, timeout, flowControl)
	}

	protected def verifyElementChecked(TestObject to, int timeout = GlobalVariable.timeOut, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.verifyElementChecked(to, timeout, flowControl)
	}

	protected def verifyElementNotChecked(TestObject to, int timeout = GlobalVariable.timeOut, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.verifyElementNotChecked(to, timeout, flowControl)
	}

	protected def verifyElementExist(TestObject to, int timeout = GlobalVariable.timeOut, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.verifyElementExist(to, timeout, flowControl)
	}

	protected def verifyElementNotExist(TestObject to, int timeout = GlobalVariable.timeOut, FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.verifyElementNotExist(to, timeout, flowControl)
	}

	protected def verifyIsLandscape(FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.verifyIsLandscape(flowControl)
	}

	protected def verifyIsPortrait(FailureHandling flowControl = FailureHandling.STOP_ON_FAILURE) {
		MobileBuiltInKeywords.verifyIsPortrait(flowControl)
	}

	protected def verifyEqual(String actualValue, String expectedValue) {
		if(actualValue.compareTo(expectedValue) == 0) {
			KeywordUtil.markPassed("Object has actual value '${actualValue}' match with expected value '${expectedValue}'")
		}
		else {
			KeywordUtil.markFailed("Object has attribute with actual value '${actualValue}' instead of expected value '${expectedValue}'")
		}
	}

	protected def verifyElementAttribute(MobileElement ele, String attribute, String expectedValue) {
		verifyEqual(ele.getAttribute(attribute), expectedValue)
	}
}