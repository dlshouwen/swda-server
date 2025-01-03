package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.Post;
import com.dlshouwen.swda.bms.system.service.IPostService;
import com.dlshouwen.swda.bms.system.vo.PostVO;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * post
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/post")
@Tag(name = "post")
@AllArgsConstructor
public class PostController {
	
	/** post service */
	private final IPostService postService;

	/**
	 * get post page result
	 * @param query
	 * @return post page result
	 */
	@PostMapping("/page")
	@Operation(name = "get post page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:post:page')")
	public R<PageResult<PostVO>> getPostPageResult(@ParameterObject @Valid Query<Post> query) {
//		get post page result
		PageResult<PostVO> pageResult = postService.getPostPageResult(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * get post list
	 * @return post list
	 */
	@PostMapping("/list")
	@Operation(name = "get post list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:post:list')")
	public R<List<PostVO>> getPostList() {
//		get post list
		List<PostVO> postList = postService.getPostList();
//		return
		return R.ok(postList);
	}

	/**
	 * get post data
	 * @param postId
	 * @return post data
	 */
	@GetMapping("/{postId}/data")
	@Operation(name = "get post data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:post:data')")
	public R<PostVO> getPostData(@PathVariable("postId") Long postId) {
//		get post data
		PostVO post = postService.getPostData(postId);
//		return post
		return R.ok(post);
	}

	/**
	 * add post
	 * @param postVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add post", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:post:add')")
	public R<String> addPost(@RequestBody PostVO postVO) {
//		add post
		postService.addPost(postVO);
//		return
		return R.ok();
	}

	/**
	 * update post
	 * @param postVO
	 * @return result
	 */
	@PutMapping("/update")
	@Operation(name = "update post", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:post:update')")
	public R<String> updatePost(@RequestBody @Valid PostVO postVO) {
//		update post
		postService.updatePost(postVO);
//		return
		return R.ok();
	}

	/**
	 * delete post
	 * @param postIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete post", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:post:delete')")
	public R<String> delete(@RequestBody List<Long> postIdList) {
//		delete post
		postService.deletePost(postIdList);
//		return
		return R.ok();
	}

	/**
	 * get post name list
	 * @param postIdList
	 * @return post name list
	 */
	@PostMapping("/name/list")
	@Operation(name = "get post name list", type = OperateType.SEARCH)
	public R<List<String>> getPostNameList(@RequestBody List<Long> postIdList) {
//		get post name list
		List<String> postNameList = postService.getPostNameList(postIdList);
//		return
		return R.ok(postNameList);
	}

}