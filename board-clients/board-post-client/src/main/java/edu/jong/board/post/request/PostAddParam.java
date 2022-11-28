package edu.jong.board.post.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class PostAddParam implements Serializable {

	private static final long serialVersionUID = 1L;

    @Schema(description = "제목")
	@NotBlank
	@Size(max = 300)
	private String title;
	
    @Schema(description = "내용")
	@NotBlank
	@Size(max = 5000)
	private String content;

}
