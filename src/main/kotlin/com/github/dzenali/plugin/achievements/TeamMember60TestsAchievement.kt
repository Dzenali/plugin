package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

object TeamMember60TestsAchievement: Achievement() {
    override fun progress(): Int {
        return 0
    }

    override fun updateProgress(progress: Int, project: Project?) {
        handleProgress(1, getTarget(), "Everyone did his/her part.", project)
    }

    override fun updateProgress(mutants: List<Mutation>, project: Project?) {

    }

    override fun getDescription(): String {
        return "Each team members must get \'Limit Testing\' achievement."
    }

    override fun getName(): String {
        return "All for one"
    }

    override fun getTarget(): Int {
        return 1
    }

    override fun getTier(): Int {
        return 2
    }
}