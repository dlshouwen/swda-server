package com.dlshouwen.swda.utils;

import com.dlshouwen.swda.entity.base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * tree utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class TreeUtils {

	/**
	 * generate
	 * <p>iterator list date to generate tree node which has inner children</p>
	 * @return tree node list
	 */
	public static List<TreeNode> generate(List<TreeNode> treeNodeList, String pid) {
//		defined data list
		List<TreeNode> dataList = null;
//		for each tree node list
		for(TreeNode treeNode : treeNodeList) {
//			if now node pid is pid
			if(treeNode.getPid().equals(pid)) {
//				if data list null then create new 
				if(dataList==null) {
					dataList = new ArrayList<>();
				}
//				add tree node
				dataList.add(treeNode);
//				search children
				treeNode.setChildren(generate(treeNodeList, treeNode.getId()));
//				set has children
				treeNode.setHasChildren(treeNode.getChildren()!=null&&!treeNode.getChildren().isEmpty());
//				set leaf
				treeNode.setLeaf(!treeNode.isHasChildren());
			}
		}
//		return
		return dataList;
	}
	
}