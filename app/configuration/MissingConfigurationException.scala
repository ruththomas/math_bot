package configuration

class MissingConfigurationException(
                                   val path :  String
                                   ) extends Throwable {
  override def getMessage : String = s"The configuration value for '$path' is missing"
}
