package com.github.dzenali.plugin.listeners

import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.project.ProjectLocator
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.github.dzenali.plugin.util.Util
import com.github.dzenali.plugin.util.Util.getTestAchievements
import com.intellij.ide.util.PropertiesComponent
import java.io.File

object AddTestsListener : BulkFileListener {
    private var filesUnderObservation = java.util.concurrent.ConcurrentHashMap<String, Int>()
    private val regex = "/\\*(?:[^*]|\\*+[^*/])*\\*+/|//.*".toRegex()

    fun progress(): Int {
        val properties = PropertiesComponent.getInstance()
        return properties.getInt(getPropertyKey(), 0)
    }

    fun getPropertyKey(): String{
        return this::class.simpleName!!
    }

    fun updateProgress(progress: Int) {
        val properties = PropertiesComponent.getInstance()
        properties.setValue(getPropertyKey(), progress, 0)
    }

    override fun before(events: MutableList<out VFileEvent>) {
        for (event in events.filter { !Util.isTestExcluded(it.path) }) {
            val vfile = event.file ?: continue
            val file = File(vfile.path)

            if (file.exists()) {
                var counter = 0

                if (event.file?.name?.endsWith("Test.java") == true) {
                    counter = countTests(file.readText().replace(regex, ""))
                }

                filesUnderObservation[event.path] = counter
            }
        }

        super.before(events)
    }

    override fun after(events: MutableList<out VFileEvent>) {
        if(events.isEmpty()) return

        val project = events[0].file?.let { ProjectLocator.getInstance().guessProjectForFile(it) }
        for (event in events.filter { !Util.isTestExcluded(it.path) }) {
            val file = File(event.path)
            if (file.exists() && event.path.endsWith("Test.java")) {
                val counter = countTests(file.readText().replace(regex, ""))

                if (counter > 0) {
                    if (filesUnderObservation.containsKey(event.path) && filesUnderObservation[event.path]!! < counter) {
                        var progress = progress()
                        progress += counter - filesUnderObservation[event.path]!!
                        updateProgress(progress)
                    }

                    filesUnderObservation[event.path] = counter
                }
            }
        }
        for (test in getTestAchievements()) {
            test.updateProgress(progress(), project)
        }
        super.after(events)
    }

    private fun countTests(string: String): Int {
        return (string.split("@Test").dropLastWhile { it.isEmpty() }.toTypedArray().size - 1).coerceAtLeast(0)
    }
}