package edu.jong.board.role.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import edu.jong.board.role.client.RoleFeignClient;
import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;
import edu.jong.board.role.service.RoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoleController implements RoleFeignClient {

	private final RoleService roleService;
	
	@Override
	public ResponseEntity<RoleDetails> addRole(RoleAddParam param) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(roleService.addRole(param));
	}

	@Override
	public ResponseEntity<RoleDetails> modifyRole(long roleNo, RoleModifyParam param) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(roleService.modifyRole(roleNo, param));
	}

	@Override
	public ResponseEntity<Void> removeRole(long roleNo) {
		roleService.removeRole(roleNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<RoleDetails> getRole(long roleNo) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(roleService.getRole(roleNo));
	}

	@Override
	public ResponseEntity<List<RoleDetails>> searchRoles(RoleSearchCond cond) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(roleService.searchRoles(cond));
	}

	@Override
	public ResponseEntity<Void> grantRoleToMember(long roleNo, long memberNo) {
		roleService.grantRoleToMember(roleNo, memberNo);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Override
	public ResponseEntity<Void> revokeRoleToMember(long roleNo, long memberNo) {
		roleService.revokeRoleToMember(roleNo, memberNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<Void> revokeRolesToMember(long memberNo) {
		roleService.revokeRolesToMember(memberNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<List<RoleDetails>> getRolesToMember(long memberNo) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(roleService.getRolesToMember(memberNo));
	}

}
