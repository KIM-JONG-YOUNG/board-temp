package edu.jong.board.role.response;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class RoleDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "권한 번호")
    private long no;
	
    @Schema(description = "권한명")
    private String name;

    @Schema(description = "허용 메소드")
    private APIMethod method;
	
    @Schema(description = "허용 URL 패턴")
    private String urlPattern;
	
    @Schema(description = "생성일시")
    private LocalDateTime createdDateTime;
	
    @Schema(description = "수정일시")
	private LocalDateTime updatedDateTime;
	
}
