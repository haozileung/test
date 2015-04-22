package com.haozileung.test.infra;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @param <T>
 * @author Haozi
 */
public class Page<T> {
	// 公共变量 //
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	// 分页参数 //
	protected Integer pageNo = 1;
	protected Integer pageSize = 1;
	protected String orderBy = null;
	protected String order = null;

	// 返回结果 //
	protected List<T> result = Collections.emptyList();
	protected Long totalCount = -1L;

	// 构造函数 //
	public Page() {
	}

	public Page(final Integer pageSize) {
		setPageSize(pageSize);
	}

	// 查询参数访问函数 //

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(final Integer pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public Integer getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils
				.isNotBlank(order));
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(final String order) {
		// 检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr)
					&& !StringUtils.equals(ASC, orderStr))
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
		}

		this.order = StringUtils.lowerCase(order);
	}

	// 访问查询结果函数 //
	public List<T> getResult() {
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getTotalPages() {
		if (totalCount < 0)
			return -1L;

		Long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	public Integer getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	public Integer getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
}