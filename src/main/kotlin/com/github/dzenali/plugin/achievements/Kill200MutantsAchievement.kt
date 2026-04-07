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
        return minOf(properties.getInt(getPropertyKey(), 0), getTarget())
    }

    override fun updateProgress(progress: Int, project: Project?) {
        if(project?.service<GamificationService>()?.getGameMode() == GameMode.TEAM) {
            val properties = PropertiesComponent.getInstance()
            properties.setValue(getPropertyKey(), progress, 0)
            handleProgress(progress, getTarget(), "You made a fine soup with your team", project)
        }
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        if(project?.service<GamificationService>()?.getGameMode() == GameMode.SOLO) {
            val nbMutants = mutants.filter { it.status == "KILLED" }.size
            val properties = PropertiesComponent.getInstance()
            properties.setValue(getPropertyKey(), nbMutants, 0)
            handleProgress(nbMutants, getTarget(), "You made a fine soup", project)
        }
    }

    override fun getDescription(): String {
        return "Kill 200 distinct mutants"
    }

    override fun getName(): String {
        return "Cut The Bone"
    }

    override fun getTarget(): Int {
        return 200
    }

    override fun getTier(): Int {
        return 1
    }
}