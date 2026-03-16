package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent

object Add10TestsAchievement: Achievement() {

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
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return "They see me testing"
    }

    override fun getDescription(): String {
        return "Write 10 tests"
    }

}