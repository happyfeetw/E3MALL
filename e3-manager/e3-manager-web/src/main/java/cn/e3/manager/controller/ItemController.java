package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3.manager.service.ItemService;
import cn.e3.pojo.TbItem;

@RestController
public class ItemController {

	// 注入service层实现类
	@Autowired
	private ItemService itemService;
	
	/*
	 * 根据商品id查询商品信息
	 * 参数: Long itemId
	 * 返回值: json格式的TbItem
	 */
	@RequestMapping("/item/{itemId}")
	public TbItem findItemById(@PathVariable Long itemId) {
		
		//调用service服务的方法
		TbItem tbItem = itemService.findItemById(itemId);
		return tbItem;
	}
	
	
	
}
