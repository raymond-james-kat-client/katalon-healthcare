package katalon.fw.lib

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

public class Page {

	private static final Page instance = new Page();

	private Map<Class,Object> map = new HashMap<Class,Object>();
	/***
	 *
	 * @param <T> page object class . Ex - LoginPage | AccountPage
	 * @param classOf - page object class . Ex - LoginPage | AccountPage
	 * @return a Page instance
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static  <T> T nav(Class<T> classOf) throws InstantiationException, IllegalAccessException {
		synchronized(instance){
			if(!instance.map.containsKey(classOf)){

				T obj = classOf.newInstance();

				instance.map.put(classOf, obj);
			}
			return (T)instance.map.get(classOf)
		}
	}

	/****
	 * This for take screenshot purpose, if isScreenShort turns off, no screenshot step is performed
	 * For evidence, each screenshot should not duplicated
	 * @return
	 */
	public static takeScreenShot() {
		if (GlobalVariable.isCapture) {
			WebUI.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
		}
	}
}
