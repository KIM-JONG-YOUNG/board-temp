package edu.jong.board.member.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.request.MemberSearchCond;
import edu.jong.board.member.response.MemberDetails;

@Validated
public interface MemberService {

	MemberDetails addMember(MemberAddParam param);
	
	MemberDetails modifyMember(long memberNo, 
			@NotNull @Valid MemberModifyParam param);
	
	void removeMember(long memberNo);
	
	MemberDetails getMember(long memberNo);

	MemberDetails getMember(
			@NotBlank String username);

	List<MemberDetails> searchMembers(
			@Valid MemberSearchCond cond);
}
