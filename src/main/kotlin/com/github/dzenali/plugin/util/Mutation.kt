package com.github.dzenali.plugin.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

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
    val mutator: String = "",
    val description: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "mutations")
data class MutationsWrapper(
    @JacksonXmlElementWrapper(useWrapping = false)
    val mutation: List<Mutation> = emptyList()
)
//data class Mutant(val sourceFile: String, val mutatedClass: String, val detected: Boolean, val status: String, val nbOfTestRun: String,
//    val lineNumber: String, val index: String, val description: String)
