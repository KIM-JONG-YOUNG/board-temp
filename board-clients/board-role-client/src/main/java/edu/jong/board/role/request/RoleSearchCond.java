package edu.jong.board.role.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import edu.jong.board.role.type.APIMethod;
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
public class RoleSearchCond implements Serializable{

	private static final long serialVersionUID = 1L;

	@Size(max = 30)
	@Pattern(regexp = "^[A-Z]+$")
	private String name;

	private APIMethod method;
	
	@Size(max = 60)
	private String urlPattern;

	private LocalDateTime from;

	private LocalDateTime to;
	
}
