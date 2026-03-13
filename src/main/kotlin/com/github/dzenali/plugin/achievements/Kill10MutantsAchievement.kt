package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.vfs.newvfs.BulkFileListener

object Kill10MutantsAchievement : Achievement(), BulkFileListener {


    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return properties.getInt(getPropertyKey(), 0)
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