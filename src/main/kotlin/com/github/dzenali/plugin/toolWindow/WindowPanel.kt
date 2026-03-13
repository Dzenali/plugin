package com.github.dzenali.plugin.toolWindow

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTabbedPane
import javax.swing.JComponent

class WindowPanel(val project: Project): JBTabbedPane() {
    private val properties = PropertiesComponent.getInstance()

    fun create(): JComponent {
        val tabbedPane = JBTabbedPane()

        val achievements = AchievementsUI.create(project)
        tabbedPane.addTab("Achievements", achievements)

        return tabbedPane
    }
}