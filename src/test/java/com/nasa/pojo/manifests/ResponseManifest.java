package com.nasa.pojo.manifests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseManifest implements Serializable {

	@JsonProperty("photo_manifest")
	private PhotoManifest photoManifest;
}