package com.example.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.elasticsearch.model.TablesInfo;

import ch.qos.logback.core.net.server.Client;


@RestController
public class ElasticController {
	
	
	@Autowired
	private TransportClient client;
	
    //Method 1: Add
	@PostMapping("/catalog/table/add")
	public ResponseEntity<String> add(@RequestBody TablesInfo tables) {
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder()
					.startObject()
					.field("id", tables.getId())
					.field("storage_type", tables.getStorage_type())
					.field("source_system", tables.getSource_system())
					.field("schema", tables.getSchema())
					.field("table_name", tables.getTable_name())
					.field("table_desc", tables.getTable_desc())			
					.field("schedule", tables.getSchedule())
					.field("row_count", tables.getRow_count())
					.field("data_as_of_date", tables.getData_as_of_date())
					.field("load_date", tables.getLoad_date())
					.field("data_domain;", tables.getData_domain())
					
					.endObject();

			IndexResponse response = this.client.prepareIndex("catalog".toLowerCase(), "table",String.valueOf(tables.getId()))
					.setSource(builder)
					.get();

			return new ResponseEntity<String>(response.getId(), HttpStatus.OK);
			
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
	//Method 2: Get By Id
	@GetMapping("/catalog/table/{id}")
	public ResponseEntity<Map<String, Object>> getByIdFromES(@PathVariable int id){
		
		GetResponse response = this.client.prepareGet("catalog".toLowerCase(), "table", String.valueOf(id)).get();		
		if (!response.isExists()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Map<String, Object>>(response.getSource(),HttpStatus.OK);
	}
	
	
	//Method 3: Update
	@PostMapping("/catalog/table1/{id}")
	public ResponseEntity<String> updateByIdFromES1(@PathVariable String id,
			String storage_type, String source_system, String schema,
			String table_name, String table_desc, String schedule, String row_count,
			String data_as_of_date, String load_date, String data_domain) throws IOException {
	

		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()			 
				.field("storage_type", storage_type)
				.field("source_system", source_system)
				.field("schema", schema)
				.field("table_name", table_name)
				.field("table_desc", table_desc)			
				.field("schedule", schedule)
				.field("row_count", row_count)
				.field("data_as_of_date", data_as_of_date)
				.field("load_date", load_date)
				.field("data_domain", data_domain)
				.endObject();

		UpdateResponse response = this.client.prepareUpdate("catalog".toLowerCase(), "table",id)
			    .setDoc(builder)
				.get();	

		return new ResponseEntity<String>(response.getResult().toString(),HttpStatus.OK);
	}
	//Method 3: Update
	@PostMapping("/catalog/table/{id}")
	public ResponseEntity<String> updateByIdFromES(@PathVariable String id,
			@RequestBody TablesInfo tables) throws IOException {
				System.out.println("table table_name"+tables.getTable_name());
				System.out.println("table schedule"+tables.getSchema());

		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()			 
					.field("storage_type", tables.getStorage_type())
					.field("source_system", tables.getSource_system())
					.field("schema", tables.getSchema())
					.field("table_name", tables.getTable_name())
					.field("table_desc", tables.getTable_desc())			
					.field("schedule", tables.getSchedule())
					.field("row_count", tables.getRow_count())
					.field("data_as_of_date", tables.getData_as_of_date())
					.field("load_date", tables.getLoad_date())
					.field("data_domain", tables.getData_domain())
				.endObject();

		UpdateResponse response = this.client.prepareUpdate("catalog".toLowerCase(), "table",id)
			    .setDoc(builder)
				.get();	

		return new ResponseEntity<String>(response.getResult().toString(),HttpStatus.OK);
	}
	//Method 4: Delete
	@DeleteMapping("/catalog/table/{id}")
	public ResponseEntity<String> delete(@PathVariable String id ){
		
	
		DeleteResponse response = this.client.prepareDelete("catalog".toLowerCase(), "table", id).get();
		return new ResponseEntity<String>(response.getResult().toString(),HttpStatus.OK);
	}
	
	//Method 5: Get All
	@GetMapping("/catalog/table/all")
	public List<Map<String, Object>> getAll(){
		
        int scrollSize = 1000;
        
        List<Map<String,Object>> allTablesLst = new ArrayList<Map<String,Object>>();
        
        SearchResponse response = null;
        
        int i = 0;
        
        while( response == null || response.getHits().hits().length != 0){
            response = client.prepareSearch("catalog".toLowerCase())
            		         .setTypes("table")
            		         .setQuery(QueryBuilders.matchAllQuery())
            		         .setSize(scrollSize)
            		         .setFrom(i * scrollSize)
            		         .execute()
            		         .actionGet();
            for(SearchHit hit : response.getHits()){
            	allTablesLst.add(hit.getSource());
            }
            i++;
        }
        return allTablesLst;		
	}	
	
	//Method 6: Get any of field 
	@GetMapping("/catalog/table/search/{input}")
	public List<Map<String, Object>> searchTableRecord(
			@PathVariable String input){
			
		MultiMatchQueryBuilder builder = QueryBuilders.multiMatchQuery(input, 
                "table_desc","table_name","data_domain");	
				builder.analyzer("standard")
				.field("table_desc",20)
				.field("table_name",20)
				.field("data_domain",20)
				.operator(Operator.AND)
				.fuzziness(Fuzziness.AUTO)
				.maxExpansions(100)
				.prefixLength(0)
				.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);

		int scrollSize = 1000;

		List<Map<String,Object>> allTablesLst = new ArrayList<Map<String,Object>>();

		SearchResponse response = null;

		int i = 0;

		while( response == null || response.getHits().hits().length != 0){
			response = client.prepareSearch("catalog".toLowerCase())
					.setTypes("table")
					.setQuery(builder)
					.setSize(scrollSize)
					.setFrom(i * scrollSize)
					.execute()
					.actionGet();
			for(SearchHit hit : response.getHits()){
				allTablesLst.add(hit.getSource());
			}
			i++;
		}
		return allTablesLst;
	}
	
//	@GetMapping("/catalog/table/search/{field_type}/{field_value}")
//	public List<Map<String, Object>> searchTableRecord(
//			@PathVariable(value = "field_type") String fieldType,
//			@PathVariable(value = "field_value") String fieldValue){
//
//		int scrollSize = 1000;
//
//		List<Map<String,Object>> allTablesLst = new ArrayList<Map<String,Object>>();
//
//		SearchResponse response = null;
//
//		int i = 0;
//
//		while( response == null || response.getHits().hits().length != 0){
//			response = client.prepareSearch("catalog".toLowerCase())
//					.setTypes("table")
//					.setQuery(QueryBuilders.matchQuery(fieldType, fieldValue))
//					.setSize(scrollSize)
//					.setFrom(i * scrollSize)
//					.execute()
//					.actionGet();
//			for(SearchHit hit : response.getHits()){
//				allTablesLst.add(hit.getSource());
//			}
//			i++;
//		}
//		return allTablesLst;
//	}
	
	//Method 7: Multi Match With Fuzzy
//		@GetMapping("/catalog/table/search/{input}")
//		public List<String> multiMatch(@PathVariable String input){
//			
//		MultiMatchQueryBuilder builder = QueryBuilders.multiMatchQuery( input, 
//				                                                        "table_desc","table_name","data_domain");	
//		builder.analyzer("standard")
//		 .field("table_desc",20)
//		 .field("table_name",20)
//		 .field("data_domain",20)
//		 .operator(Operator.OR)
//		 .fuzziness(Fuzziness.TWO)
//		 .maxExpansions(100)
//		 .prefixLength(0)
//		 .type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
//		
//		  SearchResponse response = client.prepareSearch("catalog".toLowerCase())
//			        .setTypes("table")
//			        .setQuery(builder)    
//			        //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
//			        .execute()
//			        .actionGet();
//		  List<String> result = new ArrayList();
//		  
//		  for (SearchHit hit : response.getHits()) {
//			  result.add(hit.getSourceAsString());
//		  }
//		  
//		  return result;
//		//return new ResponseEntity<String>(response.getHits().getHits()[0].getSourceAsString(),HttpStatus.OK);
//		}
}
	
