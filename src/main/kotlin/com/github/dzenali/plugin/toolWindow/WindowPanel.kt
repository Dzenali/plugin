package com.github.dzenali.plugin.toolWindow

import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.GameMode
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTabbedPane
import javax.swing.JComponent

class WindowPanel(val project: Project): JBTabbedPane() {
    private val properties = PropertiesComponent.getInstance()
    private val gamificationService = project.service<GamificationService>()

    fun create(): JComponent {
        if(properties.getValue("gamification-api-key").isNullOrBlank()) {
            return ApiSettingsUI.create(project)
        }

        val tabbedPane = JBTabbedPane()

        val achievements = AchievementsUI.create(project)
        tabbedPane.addTab("Solo", achievements)

        val achievementsTeam = AchievementsTeamUI.create(project)

        tabbedPane.addTab("Team", achievementsTeam)

        tabbedPane.addChangeListener {
            val tabIndex = tabbedPane.selectedIndex
            properties.setValue("gamification-active-tabs", tabIndex.toString())

            if(tabIndex == GameMode.SOLO.ordinal || tabIndex == GameMode.TEAM.ordinal) {
                gamificationService.setGameMode(GameMode.entries[tabIndex])
            }
        }

        val settings = SettingsUI.create(project)
        tabbedPane.addTab("Settings", settings)

        tabbedPane.selectedIndex = properties.getValue("gamification-active-tabs", "0").toInt()


        return tabbedPane
    }
}