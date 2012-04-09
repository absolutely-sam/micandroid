package ningbo.media.rest.base;

import javax.xml.bind.annotation.XmlType;

import ningbo.media.rest.WsConstants;

@XmlType(name = "WSResult",namespace = WsConstants.NS)
public class WSResult {
	
	
	// 按项目的规则进行定义, 比如4xx代表客户端参数错误，5xx代表服务端业务错误等.
	public static final String SUCCESS = "0";
	public static final String PARAMETER_ERROR = "400";

	public static final String SYSTEM_ERROR = "500";
	public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknown internal error.";

	// -- WSResult基本属性 --//
	private String code = SUCCESS;
	private String message;

	/**创建结果.*/
	@SuppressWarnings("unchecked")
	public <T extends WSResult> T setError(String resultCode,
			String resultMessage) {
		code = resultCode;
		message = resultMessage;
		return (T) this;
	}

	/**创建默认异常结果.*/
	@SuppressWarnings("unchecked")
	public <T extends WSResult> T setDefaultError() {
		return (T) setError(SYSTEM_ERROR, SYSTEM_ERROR_MESSAGE);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
