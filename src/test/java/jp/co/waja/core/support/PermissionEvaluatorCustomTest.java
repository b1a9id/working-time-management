package jp.co.waja.core.support;

import jp.co.waja.core.model.Permission;
import jp.co.waja.core.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.stream.Stream;

class PermissionEvaluatorCustomTest {

	@ParameterizedTest
	@MethodSource("argumentsProvider")
	void hasPermissionFalse(Authentication authentication, Object targetDomainObject, Object targetPermission) {
		PermissionEvaluatorCustom permissionEvaluatorCustom = new PermissionEvaluatorCustom();
		Assertions.assertFalse(permissionEvaluatorCustom.hasPermission(authentication, targetDomainObject, targetPermission));
	}

	static Stream<Arguments> argumentsProvider() {
		String roleName = Role.ADMIN.name();
		String permissionName = Permission.APPROVE_ALL_WORK_TIME.name();

		return Stream.of(
				Arguments.of(null, roleName, permissionName),
				Arguments.of(null, roleName, null),
				Arguments.of(null, null, permissionName),
				Arguments.of(null, null, null)
		);
	}

	@Nested
	class HasPermissionArgumentsNonNull {

		@InjectMocks
		private PermissionEvaluatorCustom permissionEvaluatorCustom = new PermissionEvaluatorCustom();

		@Mock
		private Authentication authentication;

		@Mock
		private Serializable targetId;

		@BeforeEach
		void beforeEach() {
			MockitoAnnotations.initMocks(this);
		}

		@ParameterizedTest
		@ValueSource(strings = {
				"SHOW_MY_PROFILE", "EDIT_MY_PASSWORD",
				"LIST_MY_WORK_TIME", "COMPLETE_MY_WORK_TIME", "EDIT_MY_WORK_TIME", "BULK_EDIT_MY_WORK_TIME",
				"LIST_TEAM_STAFF", "DESCRIBE_TEAM_STAFF", "LIST_TEAM_WORK_TIME", "APPROVE_TEAM_WORK_TIME",

				"LIST_ALL_STAFF", "CREATE_ALL_STAFF", "DESCRIBE_ALL_STAFF", "EDIT_ALL_STAFF", "DELETE_ALL_STAFF",
				"LIST_ALL_WORK_TIME", "APPROVE_ALL_WORK_TIME", "EDIT_ALL_WORK_TIME",
				"CREATE_ALL_LONG_VACATION", "DELETE_ALL_LONG_VACATION",
				"LIST_ALL_TEAM", "CREATE_ALL_TEAM", "EDIT_ALL_TEAM", "DELETE_ALL_TEAM"
		})
		void roleAdmin(Object permissionName) {
			Object targetDomainObject = "ADMIN";
			Assertions.assertTrue(permissionEvaluatorCustom.hasPermission(authentication, targetDomainObject, permissionName));
		}

		@ParameterizedTest
		@ValueSource(strings = {
				"SHOW_MY_PROFILE", "EDIT_MY_PASSWORD",
				"LIST_MY_WORK_TIME", "COMPLETE_MY_WORK_TIME", "EDIT_MY_WORK_TIME", "BULK_EDIT_MY_WORK_TIME",
				"LIST_TEAM_STAFF", "DESCRIBE_TEAM_STAFF", "LIST_TEAM_WORK_TIME", "APPROVE_TEAM_WORK_TIME"
		})
		void roleManagerTrue(Object permissionName) {
			Object targetDomainObject = "MANAGER";
			Assertions.assertTrue(permissionEvaluatorCustom.hasPermission(authentication, targetDomainObject, permissionName));
		}

		@ParameterizedTest
		@ValueSource(strings = {
				"LIST_ALL_STAFF", "CREATE_ALL_STAFF", "DESCRIBE_ALL_STAFF", "EDIT_ALL_STAFF", "DELETE_ALL_STAFF",
				"LIST_ALL_WORK_TIME", "APPROVE_ALL_WORK_TIME", "EDIT_ALL_WORK_TIME",
				"CREATE_ALL_LONG_VACATION", "DELETE_ALL_LONG_VACATION",
				"LIST_ALL_TEAM", "CREATE_ALL_TEAM", "EDIT_ALL_TEAM", "DELETE_ALL_TEAM"
		})
		void roleManagerFalse(Object permissionName) {
			Object targetDomainObject = "MANAGER";
			Assertions.assertFalse(permissionEvaluatorCustom.hasPermission(authentication, targetDomainObject, permissionName));
		}

		@ParameterizedTest
		@ValueSource(strings = {
				"SHOW_MY_PROFILE", "EDIT_MY_PASSWORD",
				"LIST_MY_WORK_TIME", "COMPLETE_MY_WORK_TIME", "EDIT_MY_WORK_TIME", "BULK_EDIT_MY_WORK_TIME"
		})
		void roleStaffTrue(Object permissionName) {
			Object targetDomainObject = "STAFF";
			Assertions.assertTrue(permissionEvaluatorCustom.hasPermission(authentication, targetDomainObject, permissionName));
		}

		@ParameterizedTest
		@ValueSource(strings = {
				"LIST_TEAM_STAFF", "DESCRIBE_TEAM_STAFF", "LIST_TEAM_WORK_TIME", "APPROVE_TEAM_WORK_TIME",
				"LIST_ALL_STAFF", "CREATE_ALL_STAFF", "DESCRIBE_ALL_STAFF", "EDIT_ALL_STAFF", "DELETE_ALL_STAFF",
				"LIST_ALL_WORK_TIME", "APPROVE_ALL_WORK_TIME", "EDIT_ALL_WORK_TIME",
				"CREATE_ALL_LONG_VACATION", "DELETE_ALL_LONG_VACATION",
				"LIST_ALL_TEAM", "CREATE_ALL_TEAM", "EDIT_ALL_TEAM", "DELETE_ALL_TEAM"
		})
		void roleStaffFalse(Object permissionName) {
			Object targetDomainObject = "STAFF";
			Assertions.assertFalse(permissionEvaluatorCustom.hasPermission(authentication, targetDomainObject, permissionName));
		}

		@Test
		void roleNotFound() {
			Object targetDomainObject = "TEST";
			Object targetPermission = "SHOW_MY_PROFILE";
			Assertions.assertFalse(permissionEvaluatorCustom.hasPermission(authentication, targetDomainObject, targetPermission));
		}

		@Test
		void permissionNotFound() {
			Object targetDomainObject = "STAFF";
			Object targetPermission = "TEST";
			Assertions.assertFalse(permissionEvaluatorCustom.hasPermission(authentication, targetDomainObject, targetPermission));
		}

		@Test
		void hasNoPermission() {
			Assertions.assertFalse(permissionEvaluatorCustom.hasPermission(authentication, targetId, "ADMIN", "EDIT_ALL_STAFF"));
		}
	}

}
