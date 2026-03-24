package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.github.dzenali.plugin.util.CoverageInfo

object Cover100LinesAchievement: Achievement() {
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
        handleProgress(progress(), 100, "You successfully covered 100 lines of code.", project)
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return("Covering Fire")
    }

    override fun getDescription(): String {
        return("Cover 100 or more lines of code with your tests")
    }

}