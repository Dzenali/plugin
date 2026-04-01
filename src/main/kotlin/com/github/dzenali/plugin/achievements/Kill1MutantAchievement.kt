package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

object Kill1MutantAchievement: Achievement() {
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
        handleProgress(nbMutants, getTarget(), "Kill your first mutant", project)
    }

    override fun getDescription(): String {
        return "Kill 1 mutant"
    }

    override fun getName(): String {
        return "As easy as pie"
    }

    override fun getTarget(): Int {
        return 1
    }

    override fun getTier(): Int {
        return 0
    }

}