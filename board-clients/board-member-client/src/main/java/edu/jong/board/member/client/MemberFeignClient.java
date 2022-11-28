package edu.jong.board.member.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.request.MemberSearchCond;
import edu.jong.board.member.response.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "사용자", description = "사용자 API 목록")
@FeignClient(name = "member-service")
public interface MemberFeignClient {

    @Operation(summary = "사용자 생성 API")
	@PostMapping(value = "/members", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MemberDetails> addMember(
			@RequestBody MemberAddParam param);

    @Operation(summary = "사용자 정보 수정 API")
	@PutMapping(value = "/members/{memberNo}", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MemberDetails> modifyMember( 
			@PathVariable long memberNo,
			@RequestBody MemberModifyParam param);

    @Operation(summary = "사용자 삭제 API")
	@DeleteMapping(value = "/members/{memberNo}")
	ResponseEntity<Void> removeMember(
			@PathVariable long memberNo);

    @Operation(summary = "사용자 조회 API")
	@GetMapping(value = "/members/{memberNo}")
	ResponseEntity<MemberDetails> getMember(
			@PathVariable long memberNo);

    @Operation(summary = "사용자 조회 API")
	@GetMapping(value = "/members/username/{username}")
	ResponseEntity<MemberDetails> getMember(
			@PathVariable String username);

    @Operation(summary = "사용자 검색 API")
	@GetMapping(value = "/members")
	ResponseEntity<List<MemberDetails>> searchMembers(
			MemberSearchCond cond);

    
}
