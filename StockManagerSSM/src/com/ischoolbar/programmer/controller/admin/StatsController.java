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

import com.ischoolbar.programmer.service.admin.SellRebackService;
import com.ischoolbar.programmer.service.admin.SellService;

/**
 * ����ͳ�ƿ�����
 * @author Administrator
 *
 */
@RequestMapping("/admin/stats")
@Controller
public class StatsController {
	
	@Autowired
	private SellService sellService;
	
	@Autowired
	private SellRebackService sellRebackService;
	
	/**
	 * ͳ��ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="stats",method=RequestMethod.GET)
	public ModelAndView stats(ModelAndView model){
		model.setViewName("stats/stats");
		return model;
	}
	
	/**
	 * ��ȡͳ������
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/get_stats",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getStats(
			@RequestParam(name="type",defaultValue="statsDay") String type
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		switch (type) {
			case "statsDay":{
				ret.put("sellData", sellService.getStats("%Y-%m-%d"));
				ret.put("sellRebackData", sellRebackService.getStats("%Y-%m-%d"));
				break;
			}
			case "statsMonth":{
				ret.put("sellData", sellService.getStats("%Y-%m"));
				ret.put("sellRebackData", sellRebackService.getStats("%Y-%m"));
				break;
			}	
			case "statsYear":{
				ret.put("sellData", sellService.getStats("%Y"));
				ret.put("sellRebackData", sellRebackService.getStats("%Y"));
				break;
			}
			default:{
				ret.put("sellData", sellService.getStats("%Y-%m-%d"));
				ret.put("sellRebackData", sellRebackService.getStats("%Y-%m-%d"));
				break;
			}
		}
		ret.put("type", "success");
		return ret;
	}
}
