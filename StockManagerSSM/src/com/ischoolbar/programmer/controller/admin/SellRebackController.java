package com.ischoolbar.programmer.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.SellReback;
import com.ischoolbar.programmer.entity.admin.SellRebackDetail;
import com.ischoolbar.programmer.entity.admin.Stock;
import com.ischoolbar.programmer.entity.admin.User;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.SellRebackService;
import com.ischoolbar.programmer.service.admin.StockService;

/**
 * �����˻������������
 * @author Administrator
 *
 */
@RequestMapping("/admin/sell_reback")
@Controller
public class SellRebackController {
	
	@Autowired
	private SellRebackService sellRebackService;
	
	@Autowired
	private StockService stockService;
	/**
	 * �����˻��������б�ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("sell_reback/list");
		return model;
	}
	
	/**
	 * ģ��������ҳ��ȡ�����˻�����Ϣ
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(name="payType",required=false) Integer payType,
			@RequestParam(name="status",required=false) Integer status,
			@RequestParam(name="minMoney",required=false) Float minMoney,
			@RequestParam(name="maxMoney",required=false) Float maxMoney,
			@RequestParam(name="operator",defaultValue="") String operator,Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("operator", operator);
		if(payType != null){
			queryMap.put("payType", payType);
		}
		if(status != null){
			queryMap.put("status", status);
		}
		if(minMoney != null){
			queryMap.put("minMoney", minMoney);
		}
		if(maxMoney != null){
			queryMap.put("maxMoney", maxMoney);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("total", sellRebackService.getTotal(queryMap));
		ret.put("rows", sellRebackService.findList(queryMap));
		return ret;
	}
	
	
	/**
	 * ��������˻�����Ϣ
	 * @param sell
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(String productList,SellReback sell,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(productList)){
			ret.put("type", "error");
			ret.put("msg", "������ѡ��һ����Ʒ��Ϣ!");
			return ret;
		}
		if(sell == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ�������˻�����Ϣ!");
			return ret;
		}
		JSONArray productArray = JSONArray.fromObject(productList);
		float money = 0;
		int num = 0;
		List<Stock> stockList = new ArrayList<Stock>();
		for(int i = 0; i < productArray.size(); i++){
			JSONObject jsonObject = productArray.getJSONObject(i);
			SellRebackDetail sellDetail = new SellRebackDetail();
			sellDetail.setProductName(jsonObject.getString("name"));
			sellDetail.setProductNum(jsonObject.getInt("productNum"));
			sellDetail.setPrice(Float.valueOf(jsonObject.getString("price")));
			sellDetail.setMoney(sellDetail.getPrice() * sellDetail.getProductNum());
			sell.getSellRebackDetailList().add(sellDetail);
			money += sellDetail.getMoney();
			num += sellDetail.getProductNum();
			Stock stock = new Stock();
			stock.setProductId(Long.valueOf(jsonObject.getInt("id")+""));
			stock.setProductNum(sellDetail.getProductNum());
			stock.setCreateTime(new Date());
			stockList.add(stock);
		}
		sell.setMoney(money);
		sell.setProductNum(num);
		User admin = (User)request.getSession().getAttribute("admin");
		sell.setOperator(admin.getUsername());
		sell.setCreateTime(new Date());
		if(sellRebackService.add(sell) <= 0){
			ret.put("type", "error");
			ret.put("msg", "���ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		//���п���������
		updateStock(stockList);
		ret.put("type", "success");
		ret.put("msg", "��ӳɹ�!");
		return ret;
	}
	
	/**
	 * �༭�����˻�����Ϣ
	 * @param product
	 * @return
	 */
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(SellReback sell){
		Map<String, String> ret = new HashMap<String, String>();
		if(sell == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ����ȷ�������˻�����Ϣ!");
			return ret;
		}
		sell.setStatus(1);
		if(sellRebackService.edit(sell) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�༭ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�༭�ɹ�!");
		return ret;
	}
	
	/**
	 * ��Ʒ������
	 * @param stockList
	 */
	private void updateStock(List<Stock> stockList){
		for(Stock stock : stockList){
			Stock existStock = stockService.findByProductId(stock.getProductId());
			if(existStock!= null){
				//������Ʒ�������
				existStock.setProductNum(existStock.getProductNum() + stock.getProductNum());
				if(existStock.getProductNum() < 0){
					existStock.setProductNum(0);
				}
				stockService.edit(existStock);
			}
			
		}
	}
	
}
