package com.github.dzenali.plugin.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

@JsonIgnoreProperties(ignoreUnknown = true)
data class Indexes(
    @JacksonXmlElementWrapper(useWrapping = false)
    val index: List<Int> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "mutation")
data class Mutation(
    @JacksonXmlProperty(isAttribute = true) val detected: Boolean = false,
    @JacksonXmlProperty(isAttribute = true) val status: String = "",
    @JacksonXmlProperty(isAttribute = true) val numberOfTestsRun: Int = 0,
    val sourceFile: String = "",
    val mutatedClass: String = "",
    val mutatedMethod: String = "",
    val lineNumber: Int = 0,
    val indexes: Indexes = Indexes(), // keep indexes in constructor
    val mutator: String = "",
    val description: String = ""
) {
    val index: Int get() = indexes.index.firstOrNull() ?: 0 // computed property
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "mutations")
data class MutationsWrapper(
    @JacksonXmlElementWrapper(useWrapping = false)
    val mutation: List<Mutation> = emptyList()
)
