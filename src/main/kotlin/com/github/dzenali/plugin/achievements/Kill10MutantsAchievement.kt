package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.vfs.newvfs.BulkFileListener

object Kill10MutantsAchievement : Achievement(), BulkFileListener {

    //Returns min between 10 and mutants killed
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return minOf(properties.getInt(getPropertyKey(), 0), 10)
    }

    override fun updateProgress(progress: Int) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress, 0)
        if( properties.getInt(getPropertyKey(),0) >= 10){
            properties.setValue(getPropertyKey() + "status", "done")
        }
    }

    override fun updateProgress(mutants: List<Mutation>) {
        val nbMutants = mutants.filter { it.status == "KILLED" }.size
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), nbMutants, 0)
        if( nbMutants >= 10){
            properties.setValue(getPropertyKey() + "status", "done")
            showAchievementNotification("bravo ma baba", null)
        }
    }

    override fun getDescription(): String {
        return "Kill 10 mutants"
    }

    override fun getName(): String {
        return "Mu-ten-ts"
    }

}