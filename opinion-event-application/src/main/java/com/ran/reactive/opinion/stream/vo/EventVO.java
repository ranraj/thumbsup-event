package com.ran.reactive.opinion.stream.vo;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ran.reactive.opinion.stream.model.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class EventVO {

    @Id
    @JsonProperty("id_str")
    private String id;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("question")
    private String question;
	
	public static EventVO parse(Event event){
    	EventVO eventVO = new EventVO();
    	eventVO.id = event.getId();
    	eventVO.question = event.getQuestion();
    	return eventVO;
    }
     
}
