package com.ischoolbar.programmer.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.Stock;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.StockService;
import com.ischoolbar.programmer.service.admin.SupplierService;

/**
 * ��Ʒ�����������
 * @author Administrator
 *
 */
@RequestMapping("/admin/stock")
@Controller
public class StockController {
	
	@Autowired
	private StockService stockService;
	@Autowired
	private SupplierService supplierService;
	/**
	 * ��Ʒ�������б�ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 9999);
		model.addObject("supplierList", supplierService.findList(queryMap));
		model.setViewName("stock/list");
		return model;
	}
	
	/**
	 * ģ��������ҳ��ȡ��Ʒ�����Ϣ
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(name="productNum",required=false) Integer productNum,
			@RequestParam(name="supplierId",required=false) Long supplierId,
			@RequestParam(name="productName",defaultValue="") String productName,Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("productName", productName);
		if(supplierId != null){
			queryMap.put("supplierId", supplierId);
		}
		if(productNum != null){
			queryMap.put("productNum", productNum);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("total", stockService.getTotal(queryMap));
		ret.put("rows", stockService.findList(queryMap));
		return ret;
	}
	
	
	/**
	 * �༭��Ʒ�����Ϣ
	 * @param stock
	 * @return
	 */
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Stock stock){
		Map<String, String> ret = new HashMap<String, String>();
		if(stock == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ����Ʒ�����Ϣ!");
			return ret;
		}
		if(stock.getProductNum() < 0){
			ret.put("type", "error");
			ret.put("msg", "��Ʒ�����������С��0!");
			return ret;
		}
		if(stockService.edit(stock) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�༭ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "��ӳɹ�!");
		return ret;
	}
	
	/**
	 * ɾ��ָ��id����Ʒ���
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id){
		Map<String, String> ret = new HashMap<String, String>();
		if(id == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��Ҫɾ������Ʒ���!");
			return ret;
		}
		try {
			if(stockService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "ɾ��ʧ�ܣ�����ϵ����Ա!");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "����Ʒ����´��ڹ���������������Ϣ�����Ƚ���Ʒ��������Ϣɾ��!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "ɾ���ɹ�!");
		return ret;
	}
	
}
