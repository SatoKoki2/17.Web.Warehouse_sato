package com.example.demo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ModelTestForGroup {

	private MockMvc MOCKMVC;

	@Autowired
	GroupController target;

	@Before
	public void setup() {
		MOCKMVC = MockMvcBuilders.standaloneSetup(target).build();
	}

	@MockBean
	private GroupService GROUPSERVICE;

	@Test
	public void isModelTest() throws Exception {
		// modelにセットされているかのテスト
		when(GROUPSERVICE.findByNo("10000")).thenReturn("SS1部");
		MOCKMVC.perform(post("/group_output?number=10000"))
				.andExpect(model().attribute("name", "SS1部"));
	}
}