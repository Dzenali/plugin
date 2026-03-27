package com.github.dzenali.plugin.toolWindow

import com.github.dzenali.plugin.achievements.Achievement
import com.github.dzenali.plugin.components.AchievementIcons
import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.Util.getAchievements
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.BorderFactory
import javax.swing.Icon
import javax.swing.JPanel

class AchievementsUI {
    companion object {
        fun create(project: Project): JPanel {
            val panel = JPanel(BorderLayout())

            val achievements = achievementList(project.service<GamificationService>())

            val scrollPane = JBScrollPane(achievements)

            scrollPane.setBorder(BorderFactory.createEmptyBorder())
            scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT)

            panel.add(scrollPane, BorderLayout.CENTER)

            return panel
        }

        private fun achievementList(gamificationService: GamificationService): JPanel {
            val panel = panel {
                row {
                        button("Reconnect") {
                            gamificationService.reconnect()
                        }.align(AlignX.CENTER)
                    }
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