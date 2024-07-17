package katalon.fw.lib

import org.codehaus.groovy.runtime.GStringImpl

import com.fasterxml.jackson.databind.ObjectMapper
import com.kms.katalon.core.configuration.RunConfiguration as RC
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.FormDataBodyParameter
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.impl.HttpFileBodyContent
import com.kms.katalon.core.testobject.impl.HttpFormDataBodyContent
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.common.RestfulClient
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.util.CryptoUtil

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import internal.GlobalVariable
import katalon.enums.BodyType
import katalon.model.WSRequest
import ro.skyah.comparator.CompareMode
import ro.skyah.comparator.JSONCompare

public class BaseService <T> {

	RequestObject request
	ResponseObject response
	ArrayList httpHeader
	String createUrl
	String deleteUrl
	String updateUrl
	String getUrl

	def T initRequestObject() {
		request = new RequestObject()
		httpHeader = new ArrayList()
		return this
	}

	def T setUrl(String url) {
		request.setRestUrl(url)
		return this
	}

	def T setBasicAuthorizationHeader(String token = GlobalVariable.encodedToken) {
		httpHeader.add(new TestObjectProperty('Authorization', ConditionType.EQUALS, token))
		return this
	}

	def T setBasicAuthorizationHeader(String username, String password) {
		String token = "$username:$password".bytes.encodeBase64().toString()
		httpHeader.add(new TestObjectProperty('Authorization', ConditionType.EQUALS, "Basic $token"))
		return this
	}

	def T setAcceptHeader(String name) {
		httpHeader.add(new TestObjectProperty('Accept', ConditionType.EQUALS, name))
		return this
	}

	def T setBasicAuthorizationHeaderEncode(String username, String encodepwd) {
		String password = CryptoUtil.decode(CryptoUtil.getDefault(encodepwd))
		String token = "$username:$password".bytes.encodeBase64().toString()
		httpHeader.add(new TestObjectProperty('Authorization', ConditionType.EQUALS, "Basic $token"))
		return this
	}

	def T setBasicTokenAuthorizationHeader(String token = GlobalVariable.basicToken) {
		httpHeader.add(new TestObjectProperty('Authorization', ConditionType.EQUALS, "Basic $token"))
		return this
	}


	def T setBearerAuthorizationHeader(String token = GlobalVariable.encodedToken) {
		httpHeader.add(new TestObjectProperty('Authorization', ConditionType.EQUALS, "Bearer $token"))
		return this
	}

	def T setJsonContentTypeHeader() {
		httpHeader.add(new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/json'))
		return this
	}

	def T setOctetStreamContentTypeHeader() {
		httpHeader.add(new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/octet-stream'))
		return this
	}

	def T setHeader(String headerName, String headerValue) {
		httpHeader.add(new TestObjectProperty(headerName, ConditionType.EQUALS, headerValue))
		return this
	}

	def T setHearders(List<Object> headers) {
		if(headers != null && headers.size() > 0) {
			for(Object header in headers) {
				setHeader(header[0], header[1])
			}
		}
		return this
	}

	def T setHeaders(Map<String, String> headers) {
		if (headers != null) {
			headers.each { key, value ->
				setHeader(key, value)
			}
		}

		return this
	}

	def T setFormDataContentTypeHeader() {
		httpHeader.add(new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'multipart/form-data'))
		return this
	}

	def T setXMLContentTypeHeader() {
		httpHeader.add(new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/xml'))
		return this
	}

	def T setContentLengthHeader(String length) {
		httpHeader.add(new TestObjectProperty('Content-Length', ConditionType.EQUALS, length))
		return this
	}

	def T setPayLoad(Object payload) {
		if(payload != null) {
			String payloadStr = payload instanceof GStringImpl || payload instanceof String ? payload : parseObjectToString(payload)
			request.setBodyContent(new HttpTextBodyContent(payloadStr.replaceAll("[\\t|\\r|\\n|\\r\\n]", "")))
		}
		return this
	}


	def T setPayLoadWithFile(String filePath) {
		request.setBodyContent(new HttpFileBodyContent(filePath))
		return this
	}

	def T setFormDataPayLoad(List<FormDataBodyParameter> params) {
		request.setBodyContent(new HttpFormDataBodyContent(params))
		return this
	}

	def T setParam(List<TestObjectProperty> params) {
		if (params != null) {
			request.setRestParameters(params)
			RestfulClient.processRequestParams(request)
		}
		return this
	}

	def T setParams(Map<String, String> params) {
		if (params != null) {
			List<TestObjectProperty> p = []
			params.each { key, value ->
				p.add(new TestObjectProperty(key, ConditionType.EQUALS, value))
			}
			setParam(p)
		}

		return this
	}

	def T sendGetRequest() {
		request.setHttpHeaderProperties(httpHeader)
		request.setRestRequestMethod('GET')
		response = WS.sendRequest(request, FailureHandling.STOP_ON_FAILURE)
		return this
	}

	def T sendPostRequest() {
		request.setHttpHeaderProperties(httpHeader)
		request.setRestRequestMethod('POST')
		response = WS.sendRequest(request, FailureHandling.STOP_ON_FAILURE)
		return this
	}

	def T sendPutRequest() {
		request.setHttpHeaderProperties(httpHeader)
		request.setRestRequestMethod('PUT')
		response = WS.sendRequest(request)
		return this
	}

	def T sendDeleteRequest() {
		request.setHttpHeaderProperties(httpHeader)
		request.setRestRequestMethod('DELETE')
		response = WS.sendRequest(request, FailureHandling.STOP_ON_FAILURE)
		return this
	}

	def T verifyStatusCode (int statusCode) {
		WS.verifyResponseStatusCode(response, statusCode)
		return this
	}

	def T validateJsonAgainstSchema(String expected) {
		WS.validateJsonAgainstSchema(response, expected)
		return this
	}

	def T verifyPropertyValue(String propertyPath, def value) {
		WS.verifyElementPropertyValue(response, propertyPath, value)
		return this
	}

	def getPropertyValue(String propertyPath) {
		return WS.getElementPropertyValue(response, propertyPath)
	}

	def int getResponseStatusCode() {
		return response.getStatusCode()
	}

	def T verifyResponseBodyContentEqual(String expected) {
		String actual = response.responseBodyContent
		WS.verifyEqual(actual, expected)
		return this
	}

	def T verifyJSONResponseBodyEqual(String expected, CompareMode... compareModes) {
		String actual = response.responseBodyContent
		JSONCompare.assertEquals(expected, actual, compareModes)
		return this
	}

	def T verifyJSONResponseBodyExactEqual(String expected) {
		verifyJSONResponseBodyEqual(expected, CompareMode.JSON_ARRAY_NON_EXTENSIBLE, CompareMode.JSON_OBJECT_NON_EXTENSIBLE)
		return this
	}

	def T verifyContentIsNull () {
		boolean check = parseResponseBodyToJsonObject().content == null || parseResponseBodyToJsonObject().content == []
		WS.verifyEqual(check, true)

		return this
	}

	def String parseObjectToString(Object payload) {
		return JsonOutput.toJson(payload).toString()
	}

	def Object parseResponseBodyToJsonObject() {
		def jsonSlurper = new JsonSlurper()
		return jsonSlurper.parseText(response.getResponseText())
	}

	def Object parseResponseBodyToClassObject(def clazz) {
		return new ObjectMapper().readValue(response.getResponseBodyContent(), clazz)
	}

	def T withFileBodyContent(String filePath) {
		HttpFileBodyContent fileBodyContent = new HttpFileBodyContent(filePath)
		request.setBodyContent(fileBodyContent)
		return this
	}

	def T create(Object payload, String url=createUrl, List<Object> headers = null, int statusCode = 200) {
		setUrl(url)
		setHearders(headers)
		setJsonContentTypeHeader()
		setPayLoad(payload)
		sendPostRequest()
		verifyStatusCode(statusCode)
		return this
	}

	def T create(WSRequest req) {
		setUrl(req.url)
		setHeaders(req.headers)
		setParams(req.params)
		switch (req.bodyType) {
			case BodyType.JSON:
				setJsonContentTypeHeader()
				setPayLoad(req.payload)
				break

			case BodyType.FORM_DATA:
				setFormDataContentTypeHeader()
				setFormDataPayLoad(req.payload)
				break

			case BodyType.XML:
				setXMLContentTypeHeader()
				setPayLoad(req.payload)
				break
			default:
				KeywordUtil.markFailedAndStop('This body type is not supported yet')
		}
		sendPostRequest()
		verifyStatusCode(req.statusCode)
		return this
	}

	def T createWithParam(String url, List<Object> headers = null, List<TestObjectProperty> params) {
		setUrl(url)
		setHearders(headers)
		setJsonContentTypeHeader()
		setParam(params)
		sendPostRequest()

		return this
	}


	def T createWithoutStatusCheck(Object payload, String url = createUrl, List<Object> headers = null) {
		setUrl(url)
		setHearders(headers)
		setJsonContentTypeHeader()
		setPayLoad(payload)
		sendPostRequest()
		return this
	}

	def T update(Object payload, String url = updateUrl, List<Object> headers = null, int statusCode = 200) {
		setUrl(url)
		setHearders(headers)
		setJsonContentTypeHeader()
		setPayLoad(payload)
		sendPutRequest()
		verifyStatusCode(statusCode)
		return this
	}

	def T updateWithoutStatusCheck(Object payload, String url = updateUrl, List<Object> headers = null) {
		setUrl(url)
		setHearders(headers)
		setJsonContentTypeHeader()
		setPayLoad(payload)
		sendPutRequest()
		return this
	}

	def T delete(String url = deleteUrl, List<Object> headers = null, int statusCode = 200) {
		setUrl(url)
		setHearders(headers)
		sendDeleteRequest()
		verifyStatusCode(statusCode)
		return this
	}

	def T deleteWithoutStatusCheck(String url = deleteUrl, List<Object> headers = null) {
		setUrl(url)
		setHearders(headers)
		sendDeleteRequest()
		return this
	}

	def T get(String url = getUrl, List<Object> headers = null, List<Object> params = null, int statusCode = 200) {
		setUrl(url)
		setHearders(headers)
		setParam(params)
		sendGetRequest()
		verifyStatusCode(statusCode)
		return this
	}

	def T get(WSRequest req) {
		setUrl(req.url)
		setHeaders(req.headers)
		setParams(req.params)
		sendGetRequest()
		verifyStatusCode(req.statusCode)
		return this
	}

	def String getExecutionProfile() {
		return RC.getExecutionProfile()
	}
}