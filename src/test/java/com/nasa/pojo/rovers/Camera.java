package com.nasa.pojo.rovers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Camera implements Serializable {

	@JsonProperty("full_name")
	private String fullName;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("rover_id")
	private int roverId;
}