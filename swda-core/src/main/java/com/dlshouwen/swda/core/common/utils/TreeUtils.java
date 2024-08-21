package com.dlshouwen.swda.core.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dlshouwen.swda.core.common.entity.TreeNode;

/**
 * tree utils
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class TreeUtils {

	/**
	 * build
	 * @param treeNodes
	 * @param pid
	 * @return
	 */
	public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Long pid) {
//		pid is not null
		AssertUtils.isNull(pid, "pid");
//		create tree list
		List<T> treeList = new ArrayList<>();
//		for each nodes
		for (T treeNode : treeNodes) {
//			if children
			if (pid.equals(treeNode.getPid())) {
//				add children
				treeList.add(findChildren(treeNodes, treeNode));
			}
		}
//		return tree list
		return treeList;
	}

	/**
	 * fined children
	 * @param treeNodes
	 * @param rootNode
	 * @return children
	 */
	private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode) {
//		for each node
		for (T treeNode : treeNodes) {
//			add children
			if (rootNode.getId().equals(treeNode.getPid())) {
				rootNode.getChildren().add(findChildren(treeNodes, treeNode));
			}
		}
//		return root
		return rootNode;
	}

	/**
	 * build
	 * @param treeNodes
	 * @return
	 */
	public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes) {
//		defined result
		List<T> result = new ArrayList<>();
//		convert to map
		Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
		for (T treeNode : treeNodes) {
			nodeMap.put(treeNode.getId(), treeNode);
		}
//		for each node
		for (T node : nodeMap.values()) {
//			get parent
			T parent = nodeMap.get(node.getPid());
//			add children
			if (parent != null && !(node.getId().equals(parent.getId()))) {
				parent.getChildren().add(node);
				continue;
			}
//			add node
			result.add(node);
		}
//		return result
		return result;
	}

}