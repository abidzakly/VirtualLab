package org.d3ifcool.virtualab.data.model

import com.squareup.moshi.Json

data class Response(

	@Json(name="projects")
	val projects: List<ProjectsItem?>? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="email")
	val email: String? = null,

	@Json(name="webinars")
	val webinars: List<WebinarsItem?>? = null
)

data class WebinarsItem(

	@Json(name="description")
	val description: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="title")
	val title: String? = null
)

data class ProjectsItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="detail")
	val detail: String? = null,

	@Json(name="id")
	val id: Int? = null
)
