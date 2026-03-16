package com.github.dzenali.plugin.util

import com.intellij.openapi.project.Project
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.sql.Timestamp

object Logger {
    private const val FILE_NAME = "EvaluationLogs.txt"

    enum class Kind {
        Notification, Main, Debug, Error
    }

    fun logStatus(text: String, kind: Kind, project: Project?) {

        if (project != null) {
            try {
                val path = Util.getEvaluationFilePath(project, FILE_NAME)
                val output: Writer = BufferedWriter(FileWriter(File(path), true))

                val timestamp = Timestamp(System.currentTimeMillis()).toString()
                val data = "$timestamp - ${kind.name} - $text"
                output.appendLine(data)
                output.close()

                println(data)
            } catch (_: Exception) {}
        }
    }
}