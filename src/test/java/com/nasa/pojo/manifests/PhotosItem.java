package com.nasa.pojo.manifests;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotosItem implements Serializable {

	@JsonProperty("cameras")
	private List<String> cameras;

	@JsonProperty("sol")
	private int sol;

	@JsonProperty("earth_date")
	private String earthDate;

	@JsonProperty("total_photos")
	private int totalPhotos;
}