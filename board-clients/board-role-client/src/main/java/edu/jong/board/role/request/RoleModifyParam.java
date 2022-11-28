package edu.jong.board.role.request;

import java.io.Serializable;

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
public class RoleModifyParam implements Serializable{

	private static final long serialVersionUID = 1L;

    @Schema(description = "허용 메소드")
	private APIMethod method;
	
    @Schema(description = "허용 URL 패턴")
	@Size(max = 60)
	private String urlPattern;

}
