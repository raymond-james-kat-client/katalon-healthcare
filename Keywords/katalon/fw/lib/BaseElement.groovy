package katalon.fw.lib

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject


public class BaseElement {

	public TestObject byName (String label) {
		return findTestObject("Common/name",[('name'):label]);
	}

	public TestObject btn (String label) {
		return findTestObject("Common/btn",[('label'):label]);
	}

	public TestObject svg (String label) {
		return findTestObject("Common/svg",[('label'):label]);
	}

	public TestObject link (String label) {
		return findTestObject("Common/link",[('label'):label]);
	}

	public TestObject txt (String label) {
		return findTestObject("Common/txt",[('label'):label]);
	}

	public TestObject text (String label) {
		return findTestObject("Common/text",[('label'):label]);
	}

	public TestObject id (String id) {
		return findTestObject("Common/id",[('id'):id]);
	}

	public TestObject css (String css) {
		return findTestObject("Common/css",[('css'):css]);
	}

	public TestObject title (String label) {
		return findTestObject("Common/title",[('label'):label]);
	}

	public TestObject cls (String label) {
		return findTestObject("Common/class",[('label'):label]);
	}

	public TestObject byType (String label) {
		return findTestObject("Common/type",[('type'):label]);
	}

	public TestObject option (String label) {
		return findTestObject("Common/option",[('label'):label]);
	}

	public TestObject placeholder (String value) {
		return findTestObject("Common/placeholder",[('value'):value]);
	}

	public TestObject xpath (String xpath) {
		def a = new TestObject("objectName").addProperty("xpath", ConditionType.EQUALS, xpath)
		return new TestObject("objectName").addProperty("xpath", ConditionType.EQUALS, xpath)
	}
	
	public TestObject dataTestId (String label) {
		return findTestObject("Common/data-testid",[('label'):label]);
	}
}