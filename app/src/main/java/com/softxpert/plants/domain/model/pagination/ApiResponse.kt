package com.softxpert.plants.domain.model.pagination

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val data: List<T>,
    val links: PaginationLinks,
    val meta: MetaInfo
)


@Serializable
data class PaginationLinks(
    val first: String?,
    val last: String?,
    val next: String? = null,
    val prev: String? = null,
    val self: String?
)


@Serializable
data class MetaInfo(
    val total: Int
)