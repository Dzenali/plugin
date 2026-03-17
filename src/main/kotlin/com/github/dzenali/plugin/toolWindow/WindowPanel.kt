package com.github.dzenali.plugin.toolWindow

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTabbedPane
import javax.swing.JComponent

class WindowPanel(val project: Project): JBTabbedPane() {
    private val properties = PropertiesComponent.getInstance()

    fun create(): JComponent {
        if(properties.getValue("gamification-api-key").isNullOrBlank()) {
            return ApiSettingsUI.create(project)
        }

        val tabbedPane = JBTabbedPane()

        val achievements = AchievementsUI.create(project)
        tabbedPane.addTab("Solo", achievements)
        val achievementsTeam = null
        if(properties.getValue("gamification-team-name").isNullOrBlank()) {
            val achievementsTeam = AchievementsTeamUI.requestTeamName(project)
        }else {
            val achievementsTeam = AchievementsTeamUI.create(project)
        }
        tabbedPane.addTab("Team", achievementsTeam)

        return tabbedPane
    }
}