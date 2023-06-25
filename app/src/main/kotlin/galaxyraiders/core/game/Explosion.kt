package galaxyraiders.core.game

import galaxyraiders.Config
import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D

object ExplosionConfig {
  private val config = Config(prefix = "GR__CORE__GAME__EXPLOSION__")

  val duration = config.get<Int>("DURATION_TICKS")
}

class Explosion(
  initialPosition: Point2D,
  initialVelocity: Vector2D,
  radius: Double,
  mass: Double
) :
  SpaceObject("Explosion", '^', initialPosition, initialVelocity, radius, mass) {
  private var lifetime = ExplosionConfig.duration.toInt()

  fun tick() {
    this.lifetime--
  }

  fun isAlive(): Boolean {
    return this.lifetime > 0
  }
}
