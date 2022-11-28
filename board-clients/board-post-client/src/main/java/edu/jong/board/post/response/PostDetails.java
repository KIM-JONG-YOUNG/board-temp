package edu.jong.board.post.response;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class PostDetails implements Serializable {

	private static final long serialVersionUID = 1L;

    @Schema(description = "번호")
	private long no;
	
    @Schema(description = "제목")
	private String title;
	
    @Schema(description = "내용")
	private String content;
	
    @Schema(description = "조회수")
	private long views;
	
    @Schema(description = "작성자")
	private PostWriter writer;
	
    @Schema(description = "생성일시")
	private LocalDateTime createdDateTime;

    @Schema(description = "수정일시")
	private LocalDateTime updatedDateTime;
	
}
