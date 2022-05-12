package com.nasa.pojo.rovers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PhotosItem implements Serializable {

	@JsonProperty("sol")
	private int sol;

	@JsonProperty("earth_date")
	private String earthDate;

	@JsonProperty("id")
	private int id;

	@JsonProperty("camera")
	private Camera camera;

	@JsonProperty("rover")
	private Rover rover;

	@JsonProperty("img_src")
	private String imgSrc;
}