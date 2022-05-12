package com.nasa.pojo.rovers;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseRover implements Serializable {

	@JsonProperty("photos")
	private List<PhotosItem> photos;
}