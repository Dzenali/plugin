package com.github.dzenali.plugin.listeners

import com.intellij.coverage.BaseCoverageSuite
import com.intellij.coverage.CoverageDataManager
import com.intellij.coverage.CoverageSuite
import com.intellij.coverage.CoverageSuiteListener
import com.intellij.coverage.CoverageSuitesBundle
import com.github.dzenali.plugin.achievements.*
import com.github.dzenali.plugin.services.GamificationService
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.github.dzenali.plugin.util.CoverageInfo
import com.github.dzenali.plugin.util.GameMode
import com.github.dzenali.plugin.util.Util
import com.intellij.openapi.components.service
import java.lang.reflect.Field

object CoverageListener: CoverageSuiteListener {
    lateinit var project: Project
    private lateinit var testRunName: String

    override fun coverageGathered(suite: CoverageSuite) {
        project = suite.project

        testRunName = (suite as BaseCoverageSuite).configuration!!.name

        super.coverageGathered(suite)
    }
    override fun beforeSuiteChosen() = Unit

    override fun afterSuiteChosen() {
        val dataManager = CoverageDataManager.getInstance(project)
        if (ApplicationManager.getApplication().isUnitTestMode) {
            return
        }

        val suitesBundle: CoverageSuitesBundle = dataManager.currentSuitesBundle ?: return
        val annotator = suitesBundle.coverageEngine.getCoverageAnnotator(project)

        val modalTask: Task.Modal = object : Task.Modal(project, "Modal Cancelable Task", false) {
            override fun run(indicator: ProgressIndicator) {
                if (annotator::class.simpleName == "JavaCoverageAnnotator") {
                    javaCoverage()
                }
            }

            fun javaCoverage() {
                val field = try {
                    annotator.javaClass.getDeclaredField("myClassCoverageInfos").also { it.isAccessible = true }
                } catch (e: NoSuchFieldException) {
                    return
                }

                // Poll until populated
                var classCoverageInfosValue: Map<Any, Any> = emptyMap()
                repeat(20) {
                    @Suppress("UNCHECKED_CAST")
                    classCoverageInfosValue = field.get(annotator) as? Map<Any, Any> ?: emptyMap()
                    if (classCoverageInfosValue.isNotEmpty()) return@repeat
                    Thread.sleep(500)
                }

                if (classCoverageInfosValue.isEmpty()) {
                    println("DEBUG: coverage map still empty after polling, giving up")
                    return
                }
                // Check for class coverage information
                println("DEBUG classCoverageInfos size: ${classCoverageInfosValue.size}")

                val gamificationService = project.service<GamificationService>()
                val gameMode = gamificationService.getGameMode()

                val runClassName = testRunName.substringBefore(',').substringAfterLast('.').removeSuffix("Test")
                for ((key, value) in classCoverageInfosValue.filter { (it.key as String).contains(runClassName)  && !Util.isTestExcluded(it.key as String) }) {
                    val coverageInfo = extractCoverageInfos(value)
                    println("DEBUG coverage key: $key")
                    Cover10LinesAchievement.takeIf { !it.isDone() }?.triggerAchievement(coverageInfo, project)
                    Cover33LinesAchievement.takeIf { !it.isDone() }?.triggerAchievement(coverageInfo, project)
                    Cover100LinesAchievement.takeIf { !it.isDone() }?.triggerAchievement(coverageInfo, project)
                    Cover200LinesAchievement.takeIf { !it.isDone() }?.triggerAchievement(coverageInfo, project)
                    gamificationService.updateCoverage(coverageInfo, key as String, testRunName, project)
                }
                println("DEBUG runClassName filter: $runClassName")


                val extensionCoverageField: Field = annotator.javaClass.getDeclaredField("myDirCoverageInfos")
                extensionCoverageField.isAccessible = true
            }
        }

        ApplicationManager.getApplication().invokeLater(fun() {
            ProgressManager.getInstance().run(modalTask)
        })
    }

    private fun extractCoverageInfos(coverageInfo: Any): CoverageInfo {
        val coveredLineCount = coverageInfo.javaClass.getMethod("getCoveredLineCount").invoke(coverageInfo) as Int
        val totalLineCount = getFieldAsInt(coverageInfo, "totalLineCount")
        val totalClassCount = getFieldAsInt(coverageInfo, "totalClassCount")
        val coveredClassCount = getFieldAsInt(coverageInfo, "coveredClassCount")
        val totalMethodCount = getFieldAsInt(coverageInfo, "totalMethodCount")
        val coveredMethodCount = getFieldAsInt(coverageInfo, "coveredMethodCount")
        val coveredBranchCount = getFieldAsInt(coverageInfo, "coveredBranchCount")
        val totalBranchCount = getFieldAsInt(coverageInfo, "totalBranchCount")
        return CoverageInfo(
            totalClassCount,
            coveredClassCount,
            totalMethodCount,
            coveredMethodCount,
            totalLineCount,
            coveredLineCount,
            totalBranchCount,
            coveredBranchCount
        )
    }

    private fun getFieldAsInt(coverageInfo: Any, fieldName: String): Int {
        val field: Field = findUnderlyingField(coverageInfo.javaClass, fieldName) ?: return 0
        field.isAccessible = true
        return field.get(coverageInfo) as Int
    }

    private fun findUnderlyingField(clazz: Class<*>, fieldName: String): Field? {
        var current = clazz

        do {
            try {
                return current.getDeclaredField(fieldName)
            } catch (_: Exception) {}
            current = current.superclass
        } while (current != null)

        return null
    }

}