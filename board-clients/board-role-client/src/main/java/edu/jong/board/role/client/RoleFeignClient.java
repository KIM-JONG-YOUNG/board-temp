package edu.jong.board.role.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "권한", description = "권한 API 목록")
@FeignClient(name = "role-service")
public interface RoleFeignClient {

    @Operation(summary = "권한 생성 API")
	@PostMapping(value = "/roles", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RoleDetails> addRole(
			@RequestBody RoleAddParam param);

    @Operation(summary = "권한 수정 API")
	@PutMapping(value = "/roles/{roleNo}", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RoleDetails> modifyRole(
			@PathVariable long roleNo,
			@RequestBody RoleModifyParam param);

    @Operation(summary = "권한 삭제 API")
	@DeleteMapping(value = "/roles/{roleNo}")
	ResponseEntity<Void> removeRole(
			@PathVariable long roleNo);

    @Operation(summary = "권한 조회 API")
	@GetMapping(value = "/roles/{roleNo}")
	ResponseEntity<RoleDetails> getRole(
			@PathVariable long roleNo);

    @Operation(summary = "권한 검색 API")
	@GetMapping(value = "/roles")
	ResponseEntity<List<RoleDetails>> searchRoles(
			RoleSearchCond cond);

    @Operation(summary = "특정 권한 부여 API")
	@PostMapping(value = "/roles/{roleNo}/members/{memberNo}")
    ResponseEntity<Void> grantRoleToMember(
    		@PathVariable long roleNo,
    		@PathVariable long memberNo);

    @Operation(summary = "특정 권한 제거 API")
	@DeleteMapping(value = "/roles/{roleNo}/members/{memberNo}")
    ResponseEntity<Void> revokeRoleToMember(
    		@PathVariable long roleNo,
    		@PathVariable long memberNo);

    @Operation(summary = "전체 권한 제거 API")
	@DeleteMapping(value = "/roles/members/{memberNo}")
    ResponseEntity<Void> revokeRolesToMember(
    		@PathVariable long memberNo);

    @Operation(summary = "전체 권한 조회 API")
	@GetMapping(value = "/roles/members/{memberNo}")
    ResponseEntity<List<RoleDetails>> getRolesToMember(
    		@PathVariable long memberNo);

}
