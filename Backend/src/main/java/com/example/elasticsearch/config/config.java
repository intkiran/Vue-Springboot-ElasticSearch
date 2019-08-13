package com.example.elasticsearch.config;

import java.net.InetAddress;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@Configuration
public class config {

	private static final Logger logger = LoggerFactory.getLogger(config.class);

	@Value("${elasticsearch.ip}")
	private String hostName;
	
	@Value("${elasticsearch.port}")
	private String port;
	
	@Value("${elasticsearch.cluster.name}")
	private String clusterName;

	@Value("${elasticsearch.pool}")
	private String poolSize;
	


	@Bean
	public TransportClient transportClient()  {

		logger.info("Elasticsearch begin to init ");
		TransportClient transportClient = null;
		try {
			//
			InetSocketTransportAddress transportAddress = new InetSocketTransportAddress(
					InetAddress.getByName(hostName), Integer.valueOf(port));

			Settings esSetting = Settings.builder()
					.put("cluster.name", clusterName)
					.put("client.transport.sniff", true)
					.put("thread_pool.search.size", Integer.parseInt(poolSize))
					//.put("xpack.security.user", username + ":" + password)
					.build();

			// Settings
			transportClient = new PreBuiltTransportClient(esSetting)
					          .addTransportAddress(transportAddress);
	
		} catch (Exception e) {
			logger.error("TransportClient create error", e);
		}
		return transportClient;
	}
	
	//vue connection
    @Bean
    public WebMvcConfigurer corsConfigurer() {
       return new WebMvcConfigurerAdapter() {
       
       @Override
       public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");

           }
       };
	}
}
