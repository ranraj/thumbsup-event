package com.ran.reactive.opinion.stream.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ran.reactive.opinion.stream.vo.EventVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Document(collection = "tweets")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Event {


	@Id
    @JsonProperty("id_str")
    private String id;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("question")
    private String question;

    @JsonProperty("image_url")
	private String profileImageUrl;
    
    @JsonProperty("user")
    private User user;

    public String getId() {
        return id;
    }
    
    public static Event parse(EventVO eventVO){
    	Event event = new Event();
    	event.id = eventVO.getId();
    	event.question = eventVO.getQuestion();    	
    	event.user = eventVO.getUser();
    	return event;
    }
     
}
