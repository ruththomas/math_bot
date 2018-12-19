package actors.messages.level

/*
 * Currently this case class is just for reference
 * todo <- implement this case class to represent the path
 * */

case class Path(superCluster: Int, galaxy: Int, starSystem: Int, planet: Int, continent: Int) {
  def wholePath: String =
    this.superCluster.toString + this.galaxy.toString + this.starSystem.toString + this.planet.toString + this.continent.toString
}
