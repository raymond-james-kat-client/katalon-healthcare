package katalon.fw.lib

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject

public class BaseElementMobile {
	protected TestObject id(String id) {
		return findTestObject("Mobile/id",[('id'):id])
	}

	protected TestObject accessibilityid(String id) {
		return findTestObject("Mobile/accessibilityid",[('accessibilityid'):id])
	}

	protected TestObject xpath(String text, String resourceId, String className = "") {
		String xpath = "//*[@class = '${className}' and (@text = '${text}' or . = '${text}') and @resource-id = '${resourceId}']"
		return findTestObject("Mobile/xpath",[('xpath'):xpath])
	}

	protected TestObject xpath(String xpath) {
		return findTestObject("Mobile/xpath",[('xpath'):xpath])
	}
	
	protected TestObject cls(String className) {
		return findTestObject("Mobile/cls",[('className'):className])
	}
}
