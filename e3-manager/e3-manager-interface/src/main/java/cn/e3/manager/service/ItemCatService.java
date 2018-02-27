package cn.e3.manager.service;

import java.util.List;

import cn.e3.utils.TreeNode;

public interface ItemCatService {

	/**
	 * 需求: 根据节点的父id查询其所属的子节点
	 * 参数: Long parentId
	 * 返回值: List<TreeNode> list(集合中包含前台页面需要树形节点.)
	 * 			前台需要节点的text属性和state属性,需要在工具类工程中创建一个对象用来保存这些属性.
	 */
	public List<TreeNode> findItemCatTreeNodeList(Long parentId);
}
