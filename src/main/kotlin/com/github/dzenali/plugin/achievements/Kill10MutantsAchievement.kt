package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.newvfs.BulkFileListener

object Kill10MutantsAchievement : Achievement() {

    //Returns min between 10 and mutants killed
    override fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return minOf(properties.getInt(getPropertyKey(), 0), 10)
    }

    override fun updateProgress(progress: Int, project: Project?) {
        TODO("Not yet implemented")
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {
        val nbMutants = mutants.filter { it.status == "KILLED" }.size
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), nbMutants, 0)
        handleProgress(nbMutants, 10, "10 mutants returned to primordial soup", project)
    }

    override fun getDescription(): String {
        return "Kill 10 mutants"
    }

    override fun getName(): String {
        return "Mu-ten-ts"
    }

}