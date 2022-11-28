package edu.jong.board.member.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import edu.jong.board.member.type.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchCond implements Serializable{

	private static final long serialVersionUID = 1L;

	@Size(max = 30)
	private String username;

	@Size(max = 30)
	private String name;

	private Gender gender;

	@Size(max = 60)
	private String email;

	private LocalDateTime from;

	private LocalDateTime to;
	
}
