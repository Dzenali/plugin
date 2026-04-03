package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.GameMode
import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

object CleanAllArchetypeAchievement: Achievement() {
    override fun progress(): Int {
        TODO("Not yet implemented")
    }

    override fun updateProgress(progress: Int, project: Project?) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress, 0)
        handleProgress(progress, getTarget(), "No more bedbugs during nighttime", project)
    }

    override fun updateProgress(
        mutants: List<Mutation>,
        project: Project?
    ) {
        if(project?.service<GamificationService>()?.getGameMode() == GameMode.SOLO) {
            val filteredMutants = mutants.filter {
                it.sourceFile.contains("Warrior.java")
                        || it.sourceFile.contains("Rogue.java")
                        || it.sourceFile.contains("Mage.java")}
            val killed = filteredMutants.filter{it.status == "KILLED"}.size
            val properties = PropertiesComponent.getInstance()
            properties.setValue(getPropertyKey(), killed, 0)
            handleProgress(killed, getTarget(), "No more bedbugs during nighttime", project)
        }
    }

    override fun getName(): String {
        return "TPK - Total Party Klean"
    }

    override fun getDescription(): String {
        return "Clean all mutants from the three players archetype"
    }

    override fun getTarget(): Int {
            return 36
    }

    override fun getTier(): Int {
        return 2
    }
}