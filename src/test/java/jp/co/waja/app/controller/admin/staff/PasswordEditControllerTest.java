package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PasswordEditControllerTest {

	@InjectMocks
	private PasswordEditController controller = new PasswordEditController();

	private MockMvc mockMvc;

	@Mock
	private StaffService staffService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
	}

	@Nested
	class passwordEdit {

		@Test
		void staffNotFound() throws Exception {
			Mockito.when(staffService.getStaff(Mockito.anyLong())).thenReturn(null);

			MvcResult result = mockMvc.perform(get("/admin/staffs/password/edit/{id}", 1L))
					.andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertThat(response.getStatus())
					.isEqualTo(302);
			assertThat(response.getContentAsString())
					.isEmpty();
			assertThat(response.getRedirectedUrl())
					.isEqualTo("/admin/staffs");
		}

		@Test
		void success() throws Exception {
			Staff staff = generateStaff("Ryosuke", "Uchitate");
			Mockito.when(staffService.getStaff(Mockito.anyLong())).thenReturn(staff);

			MvcResult result = mockMvc.perform(get("/admin/staffs/password/edit/{id}", 1L))
					.andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertThat(response.getStatus())
					.isEqualTo(200);
			assertThat(result.getModelAndView().getModel())
					.containsValues(staff);
			assertThat(response.getForwardedUrl())
					.isEqualTo("admin/staff/password/edit");
		}
	}

	private Staff generateStaff(String nameLast, String nameFirst) {
		Staff staff = new Staff();
		staff.setNameLast(nameLast);
		staff.setNameFirst(nameFirst);
		return staff;
	}

}
