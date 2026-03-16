package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.github.dzenali.plugin.util.CoverageInfo

object Cover100LinesAchievement: Achievement() {

    fun triggerAchievement(coverageInfo: CoverageInfo, project: Project?) {
        if(coverageInfo.coveredLineCount <= 0 || isDone()){
            return
        }
        var progress = progress()
        progress += coverageInfo.coveredLineCount
        updateProgress(progress)
    }
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return properties.getInt(getPropertyKey(), 0)
    }

    override fun updateProgress(progress: Int) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress, 0)
        if( properties.getInt(getPropertyKey(),0) >= 100){
            properties.setValue(getPropertyKey() + "status", "done")
            showAchievementNotification("You Covered 100 Lines of code !", null)
        }
    }

    override fun updateProgress(mutants: List<Mutation>) {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return("Covering Fire")
    }

    override fun getDescription(): String {
        return("Cover 100 or more lines of code with your tests")
    }

}