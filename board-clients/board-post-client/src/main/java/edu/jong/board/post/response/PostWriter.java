package edu.jong.board.post.response;

import java.io.Serializable;

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
public class PostWriter implements Serializable {
	
	private static final long serialVersionUID = 1L;

    @Schema(description = "번호")
	private long no;
	
    @Schema(description = "계정")
	private String username;
	
    @Schema(description = "이름")
	private String name;

}
