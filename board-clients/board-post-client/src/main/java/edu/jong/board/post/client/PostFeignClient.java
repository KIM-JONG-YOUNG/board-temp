package edu.jong.board.post.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import edu.jong.board.BoardConstants.HeaderNames;
import edu.jong.board.post.request.PostAddParam;
import edu.jong.board.post.request.PostModifyParam;
import edu.jong.board.post.request.PostSearchCond;
import edu.jong.board.post.response.PostDetails;

@FeignClient(name = "post-service")
public interface PostFeignClient {

	@PostMapping(value = "/posts",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PostDetails> addPost(
			@RequestHeader(HeaderNames.ACCESS_TOKEN) String accessToken,
			@RequestBody PostAddParam param);
	
	@PutMapping(value = "/posts/{postNo}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PostDetails> modifyPost(
			@PathVariable long postNo,
			@RequestBody PostModifyParam param);

	@PutMapping(value = "/posts/{postNo}/views/increase")
	ResponseEntity<PostDetails> increasePostViews(
			@PathVariable long postNo);

	@DeleteMapping(value = "/posts/{postNo}")
	ResponseEntity<PostDetails> removePost(
			@PathVariable long postNo);
	
	@GetMapping(value = "/posts/{postNo}")
	ResponseEntity<PostDetails> getPost(
			@PathVariable long postNo);
	
	@GetMapping(value = "/posts")
	ResponseEntity<PostDetails> searchPosts(
			PostSearchCond cond);
	
}
