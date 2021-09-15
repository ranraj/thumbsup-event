package com.ran.reactive.opinion.stream;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;



@Configuration
public class EventConfiguration {
	
	@Value("${cors.allowed.url}")
	private String crossAllowedUrl;
			 	 
    
	@Bean
	CorsWebFilter corsFilter() {

		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.addAllowedOrigin(getCrossAllowedUrl());		
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}

	public String getCrossAllowedUrl() {
		if(crossAllowedUrl != null) {
			return crossAllowedUrl.trim();
		}
		return crossAllowedUrl;
	}
	
	@Bean 
	Converter<Date, ZonedDateTime> converter() {
		return new ZonedDateTimeReadConverter();
	}
	
	public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {
	    @Override
	    public ZonedDateTime convert(Date date) {
	        return date.toInstant().atZone(ZoneOffset.UTC);
	    }
	}
}