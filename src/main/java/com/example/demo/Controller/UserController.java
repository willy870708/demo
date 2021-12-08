package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.SimpleJdbcService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	SimpleJdbcService simpleJdbcService;
	
	@PostMapping("/01")
	public List<?> qryUsers() {
		MapSqlParameterSource dataSource = new MapSqlParameterSource();
		dataSource
			.addValue("I_USER_ID", null)		
			.addValue("I_USER_NM", null)
			.addValue("I_GENRDER", null);
		
		return simpleJdbcService.doCallFunction("APUSER", "PG_USER", "FN_GET_USER_INFO", dataSource);
	}
}
