package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Order;
import com.example.demo.Service.SimpleJdbcService;

@RestController
@RequestMapping("/order")
public class OrderController{
	@Autowired
	SimpleJdbcService simpleJdbcService;
	
	@PostMapping("/01")
	public Object qryOrders() {
		MapSqlParameterSource dataSource = new MapSqlParameterSource();
		dataSource
			.addValue("I_ORDER_ID", null)		
			.addValue("I_HOSTER_ID", null)
			.addValue("I_ORDER_DATE", null)
			.addValue("I_END_DATE", null)		
			.addValue("I_ORDER_TYPE", null)
			.addValue("I_STORE_ID", null)
			.addValue("I_CONTACT_METHOD", null)		
			.addValue("I_AMOUNT_LIMIT", null)
			.addValue("I_STATUS", null)
			.addValue("I_PAGE_NO", 1)		
			.addValue("I_PAGE_SIZE", 2);
		
		Map<String, ?> result = simpleJdbcService.doCallProcdeure("APUSER", "PG_ORDER", "SP_QRY_ORDERS", dataSource);
		
		return simpleJdbcService.Convert2BeanList((List<LinkedCaseInsensitiveMap<String>>)result.get("O_RESULT"), Order.class);
	}
}
