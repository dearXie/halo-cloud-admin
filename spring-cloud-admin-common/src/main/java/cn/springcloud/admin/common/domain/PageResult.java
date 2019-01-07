package cn.springcloud.admin.common.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author homeanter homeanter@163.com
 * @Data 2019-01-07 11:12:31
 */
@Getter
@Setter
@Builder
public class PageResult<T> {
	
	private List<T> data;
	
	private Integer total;
	
	private Integer totalPage;
	
	private Integer currentPage;
	
	/**
	 * 计算总页数
	 * @param total
	 * @param pageSize
	 * @return Integer
	 * @author junchen junchen1314@foxmail.com
	 * @Data 2019-01-07 11:31:21
	 */
	public static Integer getTotalPage(Integer total,Integer pageSize) {
		return (total + pageSize - 1) / pageSize;
	}
	
	
	
}
