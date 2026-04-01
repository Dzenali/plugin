package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.GameMode
import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

object CleanDragonAchievement: Achievement() {
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return minOf(properties.getInt(getPropertyKey(), 0), getTarget())
    }

    override fun updateProgress(progress: Int, project: Project?) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress, 0)
        handleProgress(progress, getTarget(), "The dragon is relieved", project)
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        if(project?.service<GamificationService>()?.getGameMode() == GameMode.SOLO) {
            val nbMutants = mutants.filter { it.status == "KILLED" && it.sourceFile == "Dragon.java" }.size
            val properties = PropertiesComponent.getInstance()
            properties.setValue(getPropertyKey(), nbMutants, 0)
            handleProgress(nbMutants, 11, "The dragon is relieved", project)
        }
    }

    override fun getDescription(): String {
        return "Clean the dragon from mutants infesting it"
    }

    override fun getName(): String {
        return "Morpions et dragons"
    }

    override fun getTarget(): Int {
        return 11
    }

    override fun getTier(): Int {
        return 1
    }

}