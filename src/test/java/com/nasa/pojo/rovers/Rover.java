package com.nasa.pojo.rovers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Rover implements Serializable {

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("launch_date")
	private String launchDate;

	@JsonProperty("landing_date")
	private String landingDate;

	@JsonProperty("status")
	private String status;
}