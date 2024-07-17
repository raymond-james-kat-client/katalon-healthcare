package katalon.services
import internal.GlobalVariable
import katalon.fw.lib.BaseService

public class SpecialityService extends BaseService<SpecialityService> {

	private String specialityUrl = GlobalVariable.apiUrl + 'specialists'

	public SpecialityService getAllSpeciality() {
		setUrl(specialityUrl).setJsonContentTypeHeader()
				.sendGetRequest()
	}
}
