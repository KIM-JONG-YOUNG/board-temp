package edu.jong.board.role.response;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class RoleDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private long no;
	private String name;
	private APIMethod method;
	private String urlPattern;
	
	private LocalDateTime createdDateTime;
	private LocalDateTime updatedDateTime;
	
}
