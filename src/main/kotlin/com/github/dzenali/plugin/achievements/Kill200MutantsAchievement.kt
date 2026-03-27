package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.GameMode
import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

object Kill200MutantsAchievement: Achievement() {
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return minOf(properties.getInt(getPropertyKey(), 0), 200)
    }

    override fun updateProgress(progress: Int, project: Project?) {
        TODO("Not yet implemented")
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        val nbMutants = mutants.filter { it.status == "KILLED" }.size
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), nbMutants, 0)
        handleProgress(nbMutants, 200, "200 mutants returned to primordial soup", project)
    }

    override fun getDescription(): String {
        return "Kill 200 distinct mutants"
    }

    override fun getName(): String {
        return "Cut The Bone"
    }

}