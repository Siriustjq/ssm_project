package com.ischoolbar.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * ��������ϸ��Ʒʵ��
 * @author Administrator
 *
 */
@Component
public class OrderInRebackDetail {
	private Long id;
	private Long orderInRebackId;//������������id
	private String productName;//��Ʒ����
	private Float price;//��Ʒ�۸�
	private Integer productNum;//��Ʒ����
	private Float money;//����Ʒ���
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getOrderInRebackId() {
		return orderInRebackId;
	}
	public void setOrderInRebackId(Long orderInRebackId) {
		this.orderInRebackId = orderInRebackId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	
}
