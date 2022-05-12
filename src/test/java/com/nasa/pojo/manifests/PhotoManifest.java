package com.nasa.pojo.manifests;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoManifest implements Serializable {

	@JsonProperty("max_sol")
	private int maxSol;

	@JsonProperty("max_date")
	private String maxDate;

	@JsonProperty("total_photos")
	private int totalPhotos;

	@JsonProperty("name")
	private String name;

	@JsonProperty("launch_date")
	private String launchDate;

	@JsonProperty("photos")
	private List<PhotosItem> photos;

	@JsonProperty("landing_date")
	private String landingDate;

	@JsonProperty("status")
	private String status;
}