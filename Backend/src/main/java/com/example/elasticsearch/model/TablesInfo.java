package com.example.elasticsearch.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TablesInfo {
	private int id;
	private String storage_type;
	private String source_system;
	private String schema;
	private String table_name;
	private String table_desc;
	private String schedule;
	private String row_count;
	private String data_as_of_date;
	private String load_date;
	private String data_domain;

	public int getId() {
		return id ;
	}
	
	public void setId(int id) {
		this.id = id;	
	}
	public String getStorage_type() {
		return storage_type;
	}

	public void setStorage_type(String storage_type) {
		this.storage_type = storage_type;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getTable_desc() {
		return table_desc;
	}

	public void setTable_desc(String table_desc) {
		this.table_desc = table_desc;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getRow_count() {
		return row_count;
	}

	public void setRow_count(String row_count) {
		this.row_count = row_count;
	}

	public String getData_as_of_date() {
		return data_as_of_date;
	}

	public void setData_as_of_date(String data_as_of_date) {
		this.data_as_of_date = data_as_of_date;
	}

	public String getLoad_date() {
		return load_date;
	}

	public void setLoad_date(String load_date) {
		this.load_date = load_date;
	}

	public String getData_domain() {
		return data_domain;
	}

	public void setData_domain(String data_domain) {
		this.data_domain = data_domain;
	}

	

}





//package com.example.elasticsearch.model;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//
//public class TablesInfo {
//	private int id;
//	private String table_name;
//	private String table_desc;
//	private String bus_domain;
//
//	public int getId() {
//		return id ;
//	}
//	
//	public void setId(int id) {
//		this.id = id;	
//	}
//
//	public String getTable_name() {
//		return table_name;
//	}
//	public void setTable_name(String table_name) {
//		this.table_name = table_name;
//	}
//	public String getTable_desc() {
//		return table_desc;
//	}
//	public void setTable_desc(String table_desc) {
//		this.table_desc = table_desc;
//	}
//	public String getBus_domain() {
//		return bus_domain;
//	}
//	public void setBus_domain(String bus_domain) {
//		this.bus_domain = bus_domain;
//	}
//	
//}
//
//
