package com.github.dzenali.plugin.toolWindow

import com.github.dzenali.plugin.achievements.Achievement
import com.github.dzenali.plugin.components.AchievementIcons
import com.github.dzenali.plugin.components.Team
import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.Util.getAchievements
import com.github.dzenali.plugin.util.Util.getPersonalAchievements
import com.github.dzenali.plugin.util.Util.getTeamAchievements
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.JBEmptyBorder
import org.jetbrains.annotations.NonNls
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
            val properties = PropertiesComponent.getInstance()
            val gamificationService = project.service<GamificationService>()
            if(properties.getValue("gamification-team-name").isNullOrBlank()){
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
            } else {
                val users = usersList(PropertiesComponent.getInstance().getValue("gamification-team-name"), project)

                val achievements = achievementList(gamificationService)
                val scrollPane = JBScrollPane(achievements)

                val panel = JPanel(BorderLayout())

                scrollPane.setBorder(BorderFactory.createEmptyBorder())
                scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT)

                panel.add(users, BorderLayout.NORTH)
                panel.add(scrollPane, BorderLayout.CENTER)

                return panel
            }
        }


        private fun achievementList(gamificationService: GamificationService): JPanel {
            val panel = panel {
                groupRowsRange("Personal Achievements") {
                    for (achievement in getPersonalAchievements()) {
                        row{
                            icon(getAchievementIcon(achievement))
                            label(achievement.getName()).align(AlignX.LEFT)
                            contextHelp(achievement.getDescription(), achievement.getName())
                        }.resizableRow()
                    }
                }
                groupRowsRange("Team Achievements") {
                    if(!gamificationService.getTeamAchievementUnlocked()) {
                        row {
                            label("Some team member still need to kill 10 mutants").align(AlignX.LEFT)
                        }
                    } else {
                        for (teamAchievement in getTeamAchievements()) {
                            row {
                                icon(getAchievementIcon(teamAchievement))
                                label(teamAchievement.getName()).align(AlignX.LEFT)
                                contextHelp(teamAchievement.getDescription(), teamAchievement.getName())
                            }
                        }
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

        private fun usersList(teamName: @NonNls String?, project: Project): JPanel {
            val gamificationService = project.service<GamificationService>()
            val panel = panel {
                row {
                    label("Team: $teamName").align(AlignX.LEFT)
                    button("Reconnect") {
                        gamificationService.reconnect()
                    }.align(AlignX.CENTER)
                    button("LeaveTeam") {
                        gamificationService.leaveTeam()
                    }.align(AlignX.RIGHT)
                }
                for (member in Team.getUsers()) {
                    row {
                        label(member.username).align(AlignX.LEFT)
                    }.resizableRow()
                }
            }
            return panel
        }
    }
}