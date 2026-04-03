package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

object KillAllMutantsAchievement : Achievement() {
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
        handleProgress(nbMutants, mutants.size, "You killed all mutants, proving evolution is a myth.", project)
    }

    override fun getName(): String {
        return "Evolietion"
    }

    override fun getDescription(): String {
        return "Kill all mutants"
    }

    override fun getTarget(): Int {
        return 297
    }

    override fun getTier(): Int {
        return 2
    }
}