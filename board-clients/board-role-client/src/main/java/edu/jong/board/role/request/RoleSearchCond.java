package edu.jong.board.role.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import edu.jong.board.role.type.APIMethod;
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
public class RoleSearchCond implements Serializable{

	private static final long serialVersionUID = 1L;

    @Schema(description = "권한명", defaultValue = "ROLE_TEST")
	@Size(max = 30)
	@Pattern(regexp = "^[A-Z]+$")
	private String name;

    @Schema(description = "허용 메소드")
	private APIMethod method;
	
    @Schema(description = "허용 URL 패턴")
	@Size(max = 60)
	private String urlPattern;

    @Schema(description = "검색 시작 일자")
	private LocalDateTime from;

    @Schema(description = "검색 종료 일자")
	private LocalDateTime to;
	
}
