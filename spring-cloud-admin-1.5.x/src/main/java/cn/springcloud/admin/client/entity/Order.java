package cn.springcloud.admin.client.entity;

import java.util.Date;

import lombok.Data;

/**
 * 
 * @author xujin
 *
 */
@Data
public class Order {
	private Long orderNo;
	private Date createTime;
	private Date payTime;
}
