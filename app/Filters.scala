import javax.inject._

import com.digitaltangible.playguard.GuardFilter
import play.api._
import play.api.http.DefaultHttpFilters

@Singleton
class Filters @Inject()(env: Environment, guardFilter: GuardFilter) extends DefaultHttpFilters
