package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.entity.Post;
import com.dlshouwen.swda.bms.service.IPostService;
import com.dlshouwen.swda.bms.vo.PostVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * post
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("post")
@Tag(name = "post")
@AllArgsConstructor
public class PostController {
	
	/** post service */
	private final IPostService postService;

	/**
	 * get post list
	 * @param query
	 * @return post list
	 */
	@GetMapping("/datas")
	@Operation(name = "get post list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:post:list')")
	public R<PageResult<PostVO>> getPostList(@ParameterObject @Valid Query<Post> query) {
//		get post list
		PageResult<PostVO> pageResult = postService.getPostList(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * get post list
	 * @return post list
	 */
	@GetMapping("/list")
	@Operation(name = "get post list", type = OperateType.SEARCH)
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
	@GetMapping("/data/{postId}")
	@Operation(name = "get post data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:post:data')")
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
	@PreAuthorize("hasAuthority('bms:post:add')")
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
	@PreAuthorize("hasAuthority('bms:post:update')")
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
	@PreAuthorize("hasAuthority('bms:post:delete')")
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