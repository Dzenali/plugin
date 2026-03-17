package com.github.dzenali.plugin.toolWindow

import com.github.dzenali.plugin.achievements.Achievement
import com.github.dzenali.plugin.components.AchievementIcons
import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.Util.getAchievements
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.JBEmptyBorder
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.Icon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class AchievementsTeamUI {
    companion object {
        fun create(project: Project): JPanel {
            val panel = JPanel(BorderLayout())

            val achievements = achievementList()

            val scrollPane = JBScrollPane(achievements)

            scrollPane.setBorder(BorderFactory.createEmptyBorder())
            scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT)

            panel.add(scrollPane, BorderLayout.CENTER)

            return panel
        }

        fun requestTeamName(project: Project): JPanel {
            val gamificationService = project.service<GamificationService>()

            val teamPanel = JPanel(BorderLayout())

            val topPanel = JPanel()
            topPanel.layout = BoxLayout(topPanel, BoxLayout.PAGE_AXIS)

            val panel = JPanel()
            panel.border = JBEmptyBorder(10)
            panel.layout = BoxLayout(panel, BoxLayout.LINE_AXIS)

            val label = JLabel("Team name :")
            label.border = JBEmptyBorder(0, 0, 0, 10)
            panel.add(label)

            val textField = JBTextField()
            panel.add(textField)

            val validateButton = JButton("Validate")
            validateButton.addActionListener {gamificationService.joinTeam(textField.text)}
            panel.add(validateButton)

            topPanel.add(panel)

            teamPanel.add(topPanel, BorderLayout.NORTH)

            return teamPanel
        }

        private fun achievementList(): JPanel {
            val panel = panel {
                groupRowsRange("Achievements") {
                    for (achievement in getAchievements()) {
                        row{
                            icon(getAchievementIcon(achievement))
                            label(achievement.getName()).align(AlignX.LEFT)
                            contextHelp(achievement.getDescription(), achievement.getName())
                        }.resizableRow()
                    }
                }
            }
            return panel
        }

        private fun getAchievementIcon(achievement: Achievement): Icon {
            return if (!achievement.isDone()) {
                AchievementIcons.lockedIcon
            } else {
                AchievementIcons.getIcon(achievement)
            }
        }
    }
}