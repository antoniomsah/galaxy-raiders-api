package galaxyraiders.core.score

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.FileReader
import java.io.FileWriter

import galaxyraiders.core.game.Asteroid
import galaxyraiders.core.game.SpaceObject

class RoundData {
    val startTime: String = java.time.LocalDateTime.now().toString()
    var destroyedAsteroids: Int = 0
    var score: Double = 0.0

    fun destroyAsteroid(ast: Asteroid) {
        destroyedAsteroids++
        score += ast.mass * ast.radius
    }
}

class ScoreEngine() {
    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .enable(SerializationFeature.INDENT_OUTPUT)
    private val scoreboardFile = "./src/main/kotlin/galaxyraiders/core/score/Scoreboard.json"
    private val leaderboardFile = "./src/main/kotlin/galaxyraiders/core/score/Leaderboard.json"
    private val pastRounds: List<RoundData> = loadHistory()
    private val currentRound: RoundData = RoundData()

    fun destroyAsteroid(ast: SpaceObject) {
        if (ast is Asteroid) {
            currentRound.destroyAsteroid(ast)
        }
        saveScoreboard()
        saveLeaderboard()
    }

    private fun loadHistory(): List<RoundData> {
        val file = File(scoreboardFile)
        if (!file.exists()) {
            return emptyList()
        }

        return try {
            val reader = FileReader(scoreboardFile)
            objectMapper.readValue<List<RoundData>>(reader)
        } catch (e: Exception) {
            println("Failed to load Scoreboard.json: ${e.message}! assuming empty history...")
            return emptyList()
        }
    }

    private fun saveScoreboard() {
        val allRounds = pastRounds + currentRound
        val json = objectMapper.writeValueAsString(allRounds)
        val writer = FileWriter(scoreboardFile)
        writer.write(json)
        writer.close()
    }

    private fun saveLeaderboard() {
        val allRounds = pastRounds + currentRound
        val sortedRounds = allRounds.sortedByDescending { it.score }
        val top3Rounds = sortedRounds.take(3)
        val json = objectMapper.writeValueAsString(top3Rounds)
        val writer = FileWriter(leaderboardFile)
        writer.write(json)
        writer.close()
    }
}