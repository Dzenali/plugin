package be.unamur.mucoop.listeners

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFileManager

@Service(Service.Level.PROJECT)
class ProjectService (val project: Project): Disposable {
    init {
        println("project Service initialization")

        project.messageBus.connect(this).subscribe(VirtualFileManager.VFS_CHANGES, MutationsListener)
    }

    override fun dispose() = Unit
}