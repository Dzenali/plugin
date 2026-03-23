package com.github.dzenali.plugin.services

import com.github.dzenali.plugin.listeners.AddTestsListener
import com.github.dzenali.plugin.listeners.MutationsListener
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.github.dzenali.plugin.listeners.CoverageListener
import com.intellij.coverage.CoverageDataManager
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.Disposable


@Service(Service.Level.PROJECT)
class MyProjectService(project: Project): Disposable {

    init {
        println("project Service initialization")

        project.messageBus.connect(this).subscribe(VirtualFileManager.VFS_CHANGES, MutationsListener)
        project.messageBus.connect(this).subscribe(VirtualFileManager.VFS_CHANGES, AddTestsListener)
        CoverageDataManager.getInstance(project).addSuiteListener(CoverageListener, this)
    }

    override fun dispose() = Unit

    fun getRandomNumber() = (1..100).random()
}
