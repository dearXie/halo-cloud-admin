package cn.springcloud.admin.common;

import lombok.Builder;
import lombok.experimental.Tolerate;

/**
 * 统一的结果返回包装类型
 * @author xujin
 */
@Builder
public class ResultData {
	@Tolerate
	public ResultData(){}
	@Builder.Default
	private int code = 200;

	/** 消息Key */
	private String msgCode;

	/** 消息内容 */

	@Builder.Default
	private String msgContent = "success";

	/** 返回的数据 **/
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
