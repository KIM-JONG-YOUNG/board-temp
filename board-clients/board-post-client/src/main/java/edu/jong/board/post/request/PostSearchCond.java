package edu.jong.board.post.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

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
public class PostSearchCond implements Serializable {

	private static final long serialVersionUID = 1L;

	@Size(max = 300)
	private String title;
	
	@Size(max = 300)
	private String content;
	
	@Size(max = 30)
	private String writerUsername;

	@PositiveOrZero
	private long views;
	
	private LocalDateTime from;

	private LocalDateTime to;
	
}
