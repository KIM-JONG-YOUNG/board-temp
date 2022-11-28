package edu.jong.board.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import edu.jong.board.member.client.MemberFeignClient;
import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.request.MemberSearchCond;
import edu.jong.board.member.response.MemberDetails;
import edu.jong.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberFeignClient {

	private final MemberService memberService;
	
	@Override
	public ResponseEntity<MemberDetails> addMember(MemberAddParam param) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(memberService.addMember(param));
	}

	@Override
	public ResponseEntity<MemberDetails> modifyMember(long memberNo, MemberModifyParam param) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.modifyMember(memberNo, param));
	}

	@Override
	public ResponseEntity<Void> removeMember(long memberNo) {
		memberService.removeMember(memberNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<MemberDetails> getMember(long memberNo) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.getMember(memberNo));
	}

	@Override
	public ResponseEntity<MemberDetails> getMember(String username) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.getMember(username));
	}

	@Override
	public ResponseEntity<List<MemberDetails>> searchMembers(MemberSearchCond cond) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.searchMembers(cond));
	}

}
