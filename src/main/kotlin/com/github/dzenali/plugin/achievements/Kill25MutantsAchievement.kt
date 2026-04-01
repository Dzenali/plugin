package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

object Kill25MutantsAchievement: Achievement() {
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return minOf(properties.getInt(getPropertyKey(), 0), getTarget())
    }

    override fun updateProgress(progress: Int, project: Project?) {
        TODO("Not yet implemented")
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        val nbMutants = mutants.filter { it.status == "KILLED" }.size
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), nbMutants, 0)
        handleProgress(nbMutants, getTarget(), "25 mutants returned to primordial soup", project)
    }

    override fun getDescription(): String {
        return "Kill 25 mutants"
    }

    override fun getName(): String {
        return "Trim The Fat"
    }

    override fun getTarget(): Int {
        return 25
    }

    override fun getTier(): Int {
        return 0
    }
}