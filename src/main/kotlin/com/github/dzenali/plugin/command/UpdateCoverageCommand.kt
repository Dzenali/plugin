package com.github.dzenali.plugin.command

import com.github.dzenali.plugin.util.CoverageInfo

data class UpdateCoverageCommand(val payload: UpdateCoverageCommandData){
    val action = "updateCoverage"
}

data class UpdateCoverageCommandData(
    val id: String, val coverageInfo: CoverageInfo, val testedClass: String, val testName: String, val gameMode: Int
)