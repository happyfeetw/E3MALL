package cn.e3.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.manager.service.ItemCatService;
import cn.e3.mapper.TbItemCatMapper;
import cn.e3.pojo.TbItemCat;
import cn.e3.pojo.TbItemCatExample;
import cn.e3.pojo.TbItemCatExample.Criteria;
import cn.e3.utils.TreeNode;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	// 注入商品类目的Mapper接口的代理对象
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/**
	 * 需求: 根据节点的父id查询其所属的子节点
	 * 参数: Long parentId
	 * 返回值: List<TreeNode> list(集合中包含前台页面需要树形节点.)
	 * 			前台需要节点的text属性和state属性,需要在工具类工程中创建一个对象用来保存这些属性.
	 * 思考: 是否已将服务发布到zookeeper中  ---> dubbo-provider.xml
	 */	
	@Override
	public List<TreeNode> findItemCatTreeNodeList(Long parentId) {
		
		//创建TbItemCat的example对象(条件查询对象)
		TbItemCatExample example = new TbItemCatExample();
		
		//创建example的条件查询器
		Criteria criteria = example.createCriteria();
		
		//向条件查询器中封装查询条件: 根据父id查询子节点
		criteria.andParentIdEqualTo(parentId);
		
		// 执行查询,获得树形节点对象.
		List<TbItemCat> tbItemCatList = itemCatMapper.selectByExample(example);
		
		// 创建树形节点的集合,用于封装需要的树形节点的属性.
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		
		// 遍历查询获得的树形节点对象,获取前台需要的节点属性值,封装到树形节点集合中去
		for (TbItemCat tbItemCat : tbItemCatList) {
			
			// 创建树形节点对象
			TreeNode treeNode = new TreeNode();
			
			// 向树形节点对象中封装节点属性
			// 封装节点id
			treeNode.setId(tbItemCat.getId());
			// 封装节点名
			treeNode.setText(tbItemCat.getName());
			// 封装节点状态
			// 通过三元运算符,将boolean转换成String,一步到位完成判断与赋值
			treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			
			// 将节点对象封装到节点结合中去
			treeNodeList.add(treeNode);
			
		}
		
		// 将节点集合返回给前台
		return treeNodeList;
	}

}
