import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext

import internal.GlobalVariable

class ApiWebTestListener {
	def testCasePrefix = [
		"GET",
		"POST",
		"DELETE",
		"PUT"
	]

	@BeforeTestCase
	def before(TestCaseContext testCaseContext) {
		if (isApiTestCase(testCaseContext.getTestCaseId()))
			GlobalVariable.isAPIRunning = true
		if (isWebTestCase(testCaseContext.getTestCaseId()))
			GlobalVariable.isWebRunning = true
		if (isFlutterTestCase(testCaseContext.getTestCaseId()))
			GlobalVariable.isFlutterRunning = true
	}

	@AfterTestCase
	def after(TestCaseContext testCaseContext) {
		GlobalVariable.isAPIRunning = false
		GlobalVariable.isFlutterRunning = false
	}

	def isApiTestCase = { String testCaseName ->
		for (httpMethod in testCasePrefix) {
			if (testCaseName.contains(httpMethod)) {
				return true;
			}
		}
		return false
	}

	def isWebTestCase = { String testCaseName ->
		if (testCaseName.contains("WEB")) {
			return true;
		}
		return false
	}
	
	def isFlutterTestCase = { String testCaseName ->
		if (testCaseName.contains("FLUTTER")) {
			return true;
		}
		return false
	}
}