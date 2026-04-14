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
            val virtualFile = event.file ?: return
            val ioFile = File(virtualFile.path)
            if (ioFile.exists()) {
                mutants = parseMutations(ioFile)
                val project = event.file?.let { ProjectLocator.getInstance().guessProjectForFile(it) }
                project!!.service<GamificationService>().sendUserMutantKilled(mutants.filter { it.status == "KILLED" })
                for(achievement in getMutantAchievements()) {
                    achievement.takeIf { !it.isDone() }?.updateProgress(mutants, project)
                }
            }
        }
        super.after(events)
    }

    fun parseMutations(file: File): List<Mutation> {
        val xmlMapper = XmlMapper().registerKotlinModule()
        return xmlMapper.readValue(file, MutationsWrapper::class.java).mutation
    }


}