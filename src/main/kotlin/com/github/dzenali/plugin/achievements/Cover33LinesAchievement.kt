package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.CoverageInfo
import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

object Cover33LinesAchievement: Achievement() {
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
        return minOf(properties.getInt(getPropertyKey(), 0), getTarget())
    }

    override fun updateProgress(progress: Int, project: Project?) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress, 0)
        handleProgress(progress(), getTarget(), "You successfully covered 33 lines of code.", project)
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return("Duck and Cover")
    }

    override fun getDescription(): String {
        return("Cover 33 or more lines of code with your tests (coverage must be active)")
    }

    override fun getTarget(): Int {
        return 33
    }

    override fun getTier(): Int {
        return 0
    }
}