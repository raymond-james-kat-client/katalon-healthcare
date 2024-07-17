package katalon.model

import katalon.enums.BodyType

public class WSRequest {
	String url
	def payload
	Map<String, String> params
	Map<String, String> headers
	int statusCode
	BodyType bodyType

	public WSRequest() {
		this.url = ''
		this.payload = null
		this.params = null
		this.headers = null
		this.statusCode = 200
		this.bodyType = BodyType.JSON
	}
}