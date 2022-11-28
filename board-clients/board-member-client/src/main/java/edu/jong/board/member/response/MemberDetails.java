package edu.jong.board.member.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import edu.jong.board.member.type.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class MemberDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "번호")
	private long no;
	
	@Schema(description = "계정")
	private String username;

	@Schema(description = "비밀번호")
	private String password;

	@Schema(description = "이름")
	private String name;

	@Schema(description = "성별")
	private Gender gender;

	@Schema(description = "이메일")
	private String email;

    @Schema(description = "생성일시")
    private LocalDateTime createdDateTime;
	
    @Schema(description = "수정일시")
	private LocalDateTime updatedDateTime;
    
    public void erasePassword() {
    	this.password = null;
    }
}
