package edu.jong.board;

public final class BoardConstants {

	public static final String ROOT_PACKAGE = "edu.jong.board";
	
	public static class TableNames {
	
		public static final String TB_ROLE = "tb_role";
		public static final String TB_MEMBER = "tb_member";
		public static final String TB_GRANTED_ROLE = "tb_granted_role";
	}

	public static class CacheNames {
		
		public static final String ROLE = "role::";
		public static final String MEMBER = "member::";
		public static final String MEMBER_ROLES = "grantedRoles::";

		public static final String BLACKLIST = "BL::";
		public static final String REFRESH_TOKEN = "RT::";
	}
	
	public static class HeaderNames {
		
		public static final String ACCESS_TOKEN = "Access-Token";
		public static final String REFRESH_TOKEN = "Refresh-Token";
		public static final String ACCESS_CHECK_URL = "Access-Check-URL";
		public static final String ACCESS_CHECK_METHOD = "Access-Check-Method";
	}
}
