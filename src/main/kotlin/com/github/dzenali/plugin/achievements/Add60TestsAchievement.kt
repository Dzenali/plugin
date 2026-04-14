package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.GameMode
import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

object Add60TestsAchievement: Achievement() {

    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return minOf(properties.getInt(getPropertyKey(), 0), getTarget())
    }

    override fun updateProgress(progress: Int, project: Project?) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress, 0)
        if(!isDone())  handleProgress(progress(), getTarget(), "You successfully added 60 tests", project)
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        TODO("Feature Not yet implemented")
    }

    override fun getName(): String {
        return "Limit Testing"
    }

    override fun getDescription(): String {
        return "Write 60 tests"
    }

    override fun getTarget(): Int {
        return 60
    }

    override fun getTier(): Int {
        return 0
    }

}