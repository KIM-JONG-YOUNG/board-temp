package edu.jong.board.role.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;

@Validated
public interface RoleService {

	RoleDetails addRole(
			@NotNull @Valid RoleAddParam param);

	RoleDetails modifyRole(long roleNo,
			@NotNull @Valid RoleModifyParam param);

	void removeRole(long roleNo);

	RoleDetails getRole(long roleNo);

	List<RoleDetails> searchRoles(
			@Valid RoleSearchCond cond);

	void grantRoleToMember(long roleNo, long memberNo);

	void revokeRoleToMember(long roleNo, long memberNo);

	void revokeRolesToMember(long memberNo);

	List<RoleDetails> getRolesToMember(long memberNo);
	
}
