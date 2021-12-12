package com.example.demo.Service.Impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.example.demo.Service.SimpleJdbcService;

@Service
public class SimpleJdbcServiceImpl implements SimpleJdbcService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<?> doCallFunction(String schemaName, String catalogName, String functionName,
			MapSqlParameterSource mapSqlParameterSource) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(schemaName)
				.withCatalogName(catalogName).withFunctionName(functionName);

		return jdbcCall.executeFunction(List.class, mapSqlParameterSource);
	}

	@Override
	public Map<String, Object> doCallProcdeure(String schemaName, String catalogName, String prodceureName,
			MapSqlParameterSource mapSqlParameterSource) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(schemaName)
				.withCatalogName(catalogName).withProcedureName(prodceureName);

		return jdbcCall.execute(mapSqlParameterSource);
	}

	@Override
	public <T> List<?> Convert2BeanList(List<LinkedCaseInsensitiveMap<String>> srcList, Class<T> clazz) {
		List<Object> target = new ArrayList<Object>();
		srcList.stream().forEach(src -> {
			try {
				target.add(Convert2Bean(src, clazz));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return target;
	}

	private Object Convert2Bean(LinkedCaseInsensitiveMap<String> src, Class<?> clazz)
			throws InstantiationException, IllegalAccessException {
		Object classObject = clazz.newInstance();
		Map<String, Object> result = new HashMap<String, Object>();

		src.keySet().forEach(key -> {
			result.put(convert2Camel(key), src.get(key));
		});
		try {
			try {
				BeanUtils.populate(classObject, result);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return classObject;
	}

	/**
	 * convert UPPER_CASE_WORD to upperCaseWord
	 * 
	 * @param src
	 * @return target
	 */
	private String convert2Camel(String src) {
		String target = "";

		for (int index = 0; index < src.length(); index++) {
			if ('_' != src.charAt(index)) {
				target += Character.toLowerCase(src.charAt(index));
			} else {
				target += src.charAt(index + 1);
				index++;
			}
		}

		return target;
	}
}
