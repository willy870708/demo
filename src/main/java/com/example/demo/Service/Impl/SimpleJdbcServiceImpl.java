package com.example.demo.Service.Impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.example.demo.Service.SimpleJdbcService;

@Service
public class SimpleJdbcServiceImpl implements SimpleJdbcService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<?> doCallFunction(String schemaName, String catalogName, String functionName, MapSqlParameterSource mapSqlParameterSource) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(schemaName)
				.withCatalogName(catalogName)
				.withFunctionName(functionName);
		

		return jdbcCall.executeFunction(List.class, mapSqlParameterSource);
	}

	@Override
	public Map<String, ?> doCallProcdeure(String schemaName, String catalogName, String prodceureName, MapSqlParameterSource mapSqlParameterSource) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName(schemaName)
				.withCatalogName(catalogName)
				.withProcedureName(prodceureName);
		
		return jdbcCall.execute(mapSqlParameterSource);
	}
}
