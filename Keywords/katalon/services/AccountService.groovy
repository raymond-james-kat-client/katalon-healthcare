package katalon.services

import internal.GlobalVariable
import katalon.fw.lib.BaseService

public class AccountService extends BaseService<AccountService>{

	private String accountUrl = GlobalVariable.apiUrl+"account/me"

	public AccountService getAccountData(String token) {
		setUrl(accountUrl).setBearerAuthorizationHeader(token)
				.sendGetRequest()
	}
}
