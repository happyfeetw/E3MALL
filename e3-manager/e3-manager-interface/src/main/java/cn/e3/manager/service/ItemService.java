package cn.e3.manager.service;

import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.PageResult;

public interface ItemService {

	/**
	 * 需求:根据商品id查询商品信息
	 * 参数: Long itemId
	 * 返回值: TbItem
	 */
	public TbItem findItemById(Long itemId);
	
	/**
	 * 需求: 根据查询参数进行商品列表的分页查询
	 * 参数: Integer page, Integer rows
	 * 返回值: json格式的pojo对象PageResult pr.
	 */
	public PageResult findItemListByPage(Integer page, Integer rows);
	
	/**
	 * 需求: 保存商品数据
	 * 参数: TbItem item, TbItemDesc itemDesc,商品规格(后面再用)
	 * 返回值: E3mallResult
	 */
	public E3mallResult saveItem(TbItem item, TbItemDesc itemDesc);
	
}
