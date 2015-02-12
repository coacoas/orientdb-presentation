package demo;

import com.orientechnologies.orient.core.id.ORecordId;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;

import java.math.BigDecimal;

/**
 * Created by bcarlson on 2/11/15.
 */
public class Graph {
    public static void main(String[] args) {
        OrientGraphFactory factory = new OrientGraphFactory(
                "remote:localhost/VehicleHistoryGraph",
                "admin", "admin");

        OrientGraph g = factory.getTx();
        GremlinPipeline pipe = new GremlinPipeline();

        try {
            OrientVertex v = g.getVertex(new ORecordId("15:643"));

            new GremlinPipeline(v).
                    in("isMake").
                    in("isModel").
                    out("Purchased").
                    filter(new PipeFunction<Vertex, Boolean>() {
                        @Override
                        public Boolean compute(Vertex v) {
                            BigDecimal price = v.getProperty("price");
                            return price.compareTo(new BigDecimal(70_000)) > 0;
                        }
                    }).in("Bought").property("fullName").forEach(System.out::println);
            g.commit();
        } catch (Exception e) {
            e.printStackTrace();
            g.rollback();
        } finally {
            g.shutdown();
        }
    }
}
