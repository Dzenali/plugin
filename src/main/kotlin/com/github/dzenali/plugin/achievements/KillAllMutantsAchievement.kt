package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent

object KillAllMutantsAchievement : Achievement() {
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return minOf(properties.getInt(getPropertyKey(), 0), 100)
    }

    override fun updateProgress(progress: Int) {
        TODO("Not yet implemented")
    }

    override fun updateProgress(mutants: List<Mutation>) {
        val nbMutants = mutants.filter { it.status == "KILLED" }.size
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), nbMutants, 0)
        if( nbMutants >= mutants.size ){
            properties.setValue(getPropertyKey() + "status", "done")
            showAchievementNotification("You killed all mutants, proving evolution is a lie.", null)
        }
    }

    override fun getName(): String {
        return "Evolution is a myth"
    }

    override fun getDescription(): String {
        return "Kill all mutants"
    }

}