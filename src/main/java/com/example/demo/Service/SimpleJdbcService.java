package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface SimpleJdbcService {
	List<?> doCallFunction(String schemaName, String catalogName, String functionName, MapSqlParameterSource mapSqlParameterSource);
	
	Map<String, ?> doCallProcdeure(String schemaName, String catalogName, String prodceureName, MapSqlParameterSource mapSqlParameterSource);
	
}
