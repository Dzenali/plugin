package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.CoverageInfo
import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

object Cover300LinesAchievement: Achievement() {
    fun triggerAchievement(coverageInfo: CoverageInfo, project: Project) {
        if(coverageInfo.coveredLineCount <= 0 || isDone()){
            return
        }
        var progress = progress()
        progress += coverageInfo.coveredLineCount
        updateProgress(progress, project)
    }
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return properties.getInt(getPropertyKey(), 0)
    }

    override fun updateProgress(progress: Int, project: Project?) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress, 0)
        handleProgress(progress(), 300, "You successfully covered 300 lines of code.", project)
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return("Looking for a needle in a haystack")
    }

    override fun getDescription(): String {
        return("Cover 300 or more lines of code with your tests (coverage must be active)")
    }

}