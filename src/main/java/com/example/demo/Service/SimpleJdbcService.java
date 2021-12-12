package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.LinkedCaseInsensitiveMap;

public interface SimpleJdbcService {
	List<?> doCallFunction(String schemaName, String catalogName, String functionName, MapSqlParameterSource mapSqlParameterSource);
	
	Map<String, ?> doCallProcdeure(String schemaName, String catalogName, String prodceureName, MapSqlParameterSource mapSqlParameterSource);
	
	<T> List<?> Convert2BeanList(List<LinkedCaseInsensitiveMap<String>> srcList, Class<T> clazz);
}
