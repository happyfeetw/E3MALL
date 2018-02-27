package cn.e3.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.manager.service.ItemService;
import cn.e3.mapper.TbItemDescMapper;
import cn.e3.mapper.TbItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.pojo.TbItemExample;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.IDUtils;
import cn.e3.utils.PageResult;

@Service
public class ItemServiceImpl implements ItemService {

	// 注入商品Mapper的接口代理对象
	@Autowired
	private TbItemMapper tbItemMapper;
	
	// 注入商品描述Mapper的接口代理对象
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	// 根据id查询商品信息
	@Override
	public TbItem findItemById(Long itemId) {
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

	/**
	 * 需求: 根据查询参数进行商品列表的分页查询
	 * 参数: Integer page, Integer rows
	 * 返回值: pojo对象PageResult pr.
	 * 写完方法后需要思考: 此时该方法的服务是否被发布了?
	 */
	@Override
	public PageResult findItemListByPage(Integer page, Integer rows) {
		
		//创建对象模板
		TbItemExample example = new TbItemExample();
		
		//查询之前设置分页查询
		PageHelper.startPage(page, rows);
		
		//指定查询
		//pagehelper插件对list的结构进行了改变,将page对象封装到list中,成为list(page(count,pages,...),list)
		List<TbItem> itemList = tbItemMapper.selectByExample(example);
		
		//通过list集合创建pageInfo的对象,该对象中封装了分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
		
		//创建一个分页的包装类对象,来封装分页查询的结果
		PageResult pr = new PageResult();
		
		//设置总记录 rows
		pr.setRows(itemList);
		
		//设置总记录数 total: 通过pageInfo对象获取总记录数
		pr.setTotal(pageInfo.getTotal());
		
		return pr;
	}

	/**
	 * 需求: 保存商品数据
	 * 参数: TbItem item, TbItemDesc itemDesc,商品规格(后面再用)
	 * 返回值: E3mallResult
	 */
	@Override
	public E3mallResult saveItem(TbItem item, TbItemDesc itemDesc) {
		// 保存商品对象
		// 生成商品id,不能重复: 使用毫秒+随机数作为id.
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		
		// 设置商品状态: 1-正常, 2-下架, 3-删除
		item.setStatus((byte)1);
		
		// 设置商品保存时的时间.
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		
		// 保存商品对象,支持对属性做判断,支持批量插入
		int j = tbItemMapper.insertSelective(item);
		System.out.println(j);
		// 商品对形象保存完成
		
		// 保存商品描述对象
		// 设置商品描述对象的属性
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		
		// 保存商品描述对象
		itemDescMapper.insertSelective(itemDesc);
		
		return E3mallResult.ok();
	}
//	@Test
//	public void test1() {
//		
//		TbItem tbItem = new TbItem();
//		tbItem.setTitle("sssss");
//		int k = tbItemMapper.insertSelective(tbItem);
//		System.out.println("k=" + k);
//	}

}
