package com.bagas.project.filmbase.data.responses

import com.google.gson.annotations.SerializedName

data class GenresResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
