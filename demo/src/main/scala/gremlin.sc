import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import com.tinkerpop.gremlin.scala.GremlinScalaPipeline

import scala.math.BigDecimal._

/**********************************************/

val g = TinkerGraphFactory.createTinkerGraph()
GremlinScalaPipeline(g).V.outE.toList().
  foreach(println)

g.shutdown()

/**********************************************/

val graph = new OrientGraph("remote:localhost/VehicleHistoryGraph",
  "admin",
  "admin")

GremlinScalaPipeline(graph).V.filter { v =>
  "Jeep" == v.getProperty[String]("name")
}.in("isMake").
  in("isModel").as("x").
  out("Purchased").filter { v =>
  val price: BigDecimal =
    v.getProperty[java.math.BigDecimal]("price")
  price > 70000.0
}.in("Bought").property[String]("fullName").toList().
  foreach(println)

graph.shutdown()
