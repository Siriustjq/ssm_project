package com.ischoolbar.programmer.entity.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * �����˻���ʵ��
 * @author Administrator
 *
 */
@Component
public class SellReback {
	private Long id;
	private Float money;//�����˻����ܽ��
	private Integer productNum;//��Ʒ����
	private int payType = 0;//֧����ʽ��0���ֽ�1������ת�ˣ�2��֧������3��΢�ţ�4��֧Ʊ��5������
	private int status = 0;//״̬��0��δ֧����1����֧��
	private String operator;//������
	private String remark;//��ע
	private List<SellRebackDetail> sellRebackDetailList = new ArrayList<SellRebackDetail>();
	private Date createTime;//�����˻�ʱ��
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public List<SellRebackDetail> getSellRebackDetailList() {
		return sellRebackDetailList;
	}
	public void setSellRebackDetailList(List<SellRebackDetail> sellRebackDetailList) {
		this.sellRebackDetailList = sellRebackDetailList;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
