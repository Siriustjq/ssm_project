package com.ischoolbar.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.admin.OrderInReback;

/**
 * �����˻���service�ӿ�
 * @author Administrator
 *
 */
@Service
public interface OrderInRebackService {
	public int add(OrderInReback orderInReback);
	public int edit(OrderInReback orderInReback);
	public List<OrderInReback> findList(Map<String, Object> queryMap);
	public Integer getTotal(Map<String, Object> queryMap);
	public int delete(Long id);
}
