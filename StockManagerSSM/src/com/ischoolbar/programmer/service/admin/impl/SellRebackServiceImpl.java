package com.ischoolbar.programmer.service.admin.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.admin.SellRebackDao;
import com.ischoolbar.programmer.entity.admin.SellReback;
import com.ischoolbar.programmer.entity.admin.SellRebackDetail;
import com.ischoolbar.programmer.service.admin.SellRebackService;
/**
 * �����˻���serviceʵ����
 */
@Service
public class SellRebackServiceImpl implements SellRebackService {

	@Autowired
	private SellRebackDao sellRebackDao;

	@Override
	public int add(SellReback sellReback) {
		// TODO Auto-generated method stub
		int rst = sellRebackDao.add(sellReback);
		if(rst > 0){
			//��ʾ�����˻�����ӳɹ�,��������������˻�������
			for(SellRebackDetail sellRebackDetail : sellReback.getSellRebackDetailList()){
				sellRebackDetail.setSellRebackId(sellReback.getId());
				sellRebackDao.addDetail(sellRebackDetail);
			}
		}
		return rst;
	}

	@Override
	public int edit(SellReback sellReback) {
		// TODO Auto-generated method stub
		return sellRebackDao.edit(sellReback);
	}

	@Override
	public List<SellReback> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return sellRebackDao.findList(queryMap);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return sellRebackDao.getTotal(queryMap);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return sellRebackDao.delete(id);
	}

	@Override
	public List<Map<String,String>> getStats(String date) {
		// TODO Auto-generated method stub
		return sellRebackDao.getStats(date);
	}

	
	
}
