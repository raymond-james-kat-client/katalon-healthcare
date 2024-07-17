package katalon.services

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.util.CryptoUtil

import internal.GlobalVariable
import katalon.fw.lib.BaseService

public class SignInService extends BaseService<SignInService> {

	private String signInUrl = GlobalVariable.apiUrl+"auth/login"

	public SignInService signIn(String email, String password) {
		String pwd = CryptoUtil.decode(CryptoUtil.getDefault(password))
		setUrl(signInUrl)
				.setJsonContentTypeHeader()
				.setPayLoad('{"email": "'+email+'","password": "'+pwd+'","rememberMe": true}')
				.sendPostRequest()
	}

	public String getAccessToken() {
		GlobalVariable.accessToken = parseResponseBodyToJsonObject().data.accessToken.toString()
		return parseResponseBodyToJsonObject().data.accessToken.toString()
	}
}
