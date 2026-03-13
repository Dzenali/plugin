package be.unamur.mucoop.listeners

import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.dzenali.plugin.util.Mutation
import com.github.dzenali.plugin.util.MutationsWrapper
import com.github.dzenali.plugin.util.Util.getAchievements
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
            mutants = parseMutations(event.path)

            for(achievement in getAchievements()) {
                if(!achievement.isDone()) achievement.updateProgress(mutants)
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