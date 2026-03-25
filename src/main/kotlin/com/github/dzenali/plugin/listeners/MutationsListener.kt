package com.github.dzenali.plugin.listeners

import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.util.Mutation
import com.github.dzenali.plugin.util.MutationsWrapper
import com.github.dzenali.plugin.util.Util.getAchievements
import com.github.dzenali.plugin.util.Util.getMutantAchievements
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectLocator
import java.io.File

object MutationsListener : BulkFileListener {
    private var mutants:List<Mutation> = emptyList()

    override fun before(events: MutableList<out VFileEvent>) {
        mutants = emptyList()
        super.before(events)
    }

    override fun after(events: MutableList<out VFileEvent>) {
        val event = events.firstOrNull { it.path.endsWith("mutations.xml")}
        if (event != null) {
            val file = File(event.path)
            if (file.exists()) {
                mutants = parseMutations(event.path)
                val project = event.file?.let { ProjectLocator.getInstance().guessProjectForFile(it) }
                project!!.service<GamificationService>().sendUserMutantKilled(mutants.filter { it.status == "KILLED" })
                for(achievement in getMutantAchievements()) {
                    if(!achievement.isDone()) achievement.updateProgress(mutants, project)
                }
            }
        }
        super.after(events)
    }

    fun parseMutations(filePath: String): List<Mutation> {
        val xmlMapper = XmlMapper().registerKotlinModule()
        val wrapper = xmlMapper.readValue(File(filePath), MutationsWrapper::class.java)
        return wrapper.mutation
    }


}