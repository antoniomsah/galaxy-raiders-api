package galaxyraiders.core.physics

data class Point2D(val x: Double, val y: Double) {
  operator fun plus(p: Point2D): Point2D {
	return Point2D(x=this.x+p.x, y=this.y+p.y);
  }

  operator fun plus(v: Vector2D): Point2D {
    return Point2D(x=this.x+v.dx, y=this.y+v.dy);
  }

  override fun toString(): String {
    return "Point2D(x=$x, y=$y)"
  }

  fun toVector(): Vector2D {
    return Vector2D(dx=this.x,dy=this.y);
  }

  fun impactVector(p: Point2D): Vector2D {
    return Vector2D(dx=p.x-this.x, dy=p.y-this.y);
  }

  fun impactDirection(p: Point2D): Vector2D {
    return (this.impactVector(p)).unit
  }

  fun contactVector(p: Point2D): Vector2D {
    return INVALID_VECTOR
  }

  fun contactDirection(p: Point2D): Vector2D {
    return INVALID_VECTOR
  }

  fun distance(p: Point2D): Double {
	return Vector2D(dx=p.x-this.x, dy=p.y-this.y).magnitude;
  }
}
