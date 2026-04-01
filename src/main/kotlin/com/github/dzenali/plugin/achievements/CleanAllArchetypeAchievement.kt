package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.openapi.project.Project

object CleanAllArchetypeAchievement: Achievement() {
    override fun progress(): Int {
        TODO("Not yet implemented")
    }

    override fun updateProgress(progress: Int, project: Project?) {
        TODO("Not yet implemented")
    }

    override fun updateProgress(
        mutants: List<Mutation>,
        project: Project?
    ) {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getDescription(): String {
        TODO("Not yet implemented")
    }

    override fun getTarget(): Int {
        TODO("Not yet implemented")
    }

    override fun getTier(): Int {
        return 2
    }
}