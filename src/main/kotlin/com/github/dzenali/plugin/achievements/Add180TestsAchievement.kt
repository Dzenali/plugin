package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

object Add180TestsAchievement: Achievement() {
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return minOf(properties.getInt(getPropertyKey(), 0), getTarget())
    }

    override fun updateProgress(progress: Int, project: Project?) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress + progress(), 0)
        handleProgress(progress(), getTarget(), "No More Ariane disaster", project)
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        TODO("Feature Not yet implemented")
    }

    override fun getName(): String {
        return "No value type mismatch"
    }

    override fun getDescription(): String {
        return "Write 180 tests as a team"
    }

    override fun getTarget(): Int {
        return 180
    }

    override fun getTier(): Int {
        return 1
    }

}