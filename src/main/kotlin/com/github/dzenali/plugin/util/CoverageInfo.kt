package com.github.dzenali.plugin.util

class CoverageInfo(
    val totalClassCount: Int, val coveredClassCount: Int, val totalMethodCount: Int, val coveredMethodCount: Int,
    val totalLineCount: Int, val coveredLineCount: Int, val totalBranchCount: Int, val coveredBranchCount: Int
) {
    fun isEmpty(): Boolean {
        return (coveredClassCount == 0 && coveredMethodCount == 0 && coveredLineCount == 0 && coveredBranchCount == 0)
    }

    fun getAsCsvHeader(filePath: String): List<String> {
        return listOf(
            "totalClassCount",
            "coveredClassCount",
            "totalMethodCount",
            "coveredMethodCount",
            "totalLineCount",
            "coveredLineCount",
            "totalBranchCount",
            "coveredBranchCount",
            filePath
        )
    }

    fun getAsCsvData(): String{
        return "$totalClassCount,$coveredClassCount,$totalMethodCount,$coveredMethodCount,$totalLineCount,$coveredLineCount,$totalBranchCount,$coveredBranchCount"
    }

    override fun toString(): String {
        return "CoverageInfo(totalClassCount=$totalClassCount, coveredClassCount=$coveredClassCount, totalMethodCount=$totalMethodCount, coveredMethodCount=$coveredMethodCount, totalLineCount=$totalLineCount, coveredLineCount=$coveredLineCount, totalBranchCount=$totalBranchCount, coveredBranchCount=$coveredBranchCount)"
    }
}