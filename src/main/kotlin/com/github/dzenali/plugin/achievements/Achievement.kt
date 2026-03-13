package com.github.dzenali.plugin.achievements

import com.github.dzenali.plugin.toolWindow.WindowPanel
import com.github.dzenali.plugin.util.Mutation
import com.intellij.ide.DataManager
import com.intellij.ide.util.PropertiesComponent
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.ContentFactory
import java.util.concurrent.TimeUnit
import javax.swing.SwingUtilities

abstract class Achievement {

    // How much of the required actions have been performed
    abstract fun progress(): Int

    abstract fun updateProgress(progress: Int)

    abstract fun updateProgress(mutants: List<Mutation>)

    abstract fun getName(): String

    abstract fun getDescription(): String

    fun handleProgress() {

    }

    fun showAchievementNotification(message: String, project: Project?) {
        val group = NotificationGroupManager.getInstance().getNotificationGroup("Gamification")
        val notification = group.createNotification(
            message,
            NotificationType.INFORMATION
        )
            .addAction(
                NotificationAction.createSimple("Show more information") {
                    val myProject = DataManager.getInstance().dataContextFromFocusAsync.blockingGet(10, TimeUnit.SECONDS)!!.getData(
                        PlatformDataKeys.PROJECT)
                    val toolWindow = ToolWindowManager.getInstance(myProject!!).getToolWindow("Gamification")!!
                    refreshWindow()
                    toolWindow.show()
                }
            )

        notification.notify(null)

    }

    protected fun refreshWindow(){
        val project = DataManager.getInstance().dataContextFromFocusAsync.blockingGet(10, TimeUnit.SECONDS)!!.getData(PlatformDataKeys.PROJECT)
        val toolWindow = ToolWindowManager.getInstance(project!!).getToolWindow("Gamification")!!

        SwingUtilities.invokeLater {
            toolWindow.contentManager.removeAllContents(true)
            val panel = WindowPanel(project).create()
            val content = ContentFactory.getInstance().createContent(panel, null, false)
            toolWindow.contentManager.addContent(content)
        }
    }

    fun isDone(): Boolean {
        val properties = PropertiesComponent.getInstance()
        val isDone: String? = properties.getValue(getPropertyKey() + "status")
        return !(isDone == null || isDone != "done")
    }

    open fun getPropertyKey(): String{
        return this::class.simpleName!!
    }


}