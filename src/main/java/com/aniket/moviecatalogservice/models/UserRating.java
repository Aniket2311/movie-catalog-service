package com.aniket.moviecatalogservice.models;

import java.util.List;

public class UserRating {

	private List<Rating> useRatings;

	public List<Rating> getUseRatings() {
		return useRatings;
	}

	public void setUseRatings(List<Rating> useRatings) {
		this.useRatings = useRatings;
	}
}
