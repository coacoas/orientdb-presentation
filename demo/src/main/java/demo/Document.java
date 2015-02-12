package demo;

import com.orientechnologies.orient.core.command.OCommandResultListener;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLAsynchQuery;

public class Document {
    public static void main(String[] args) {

        ODatabaseDocumentTx db =
                new ODatabaseDocumentTx("remote:localhost/VehicleHistoryGraph").
                open("admin", "admin");

        try {
            db.begin();

//            ODocument doc = new ODocument("Doc").
//                    field("firstname", "Bill").
//                    field("lastname", "Carlson");
//
//            doc.save();
//
//            OSQLSynchQuery<ODocument> q =
//                    new OSQLSynchQuery<>("select count(*) from Doc");
//            for (ODocument i : q.run()) {
//                System.out.println("Found " + i);
//            }

            new OSQLAsynchQuery<String>("select expand(in('Bought')) from (select from (select expand(in('isMake').in('isModel').out('Purchased')) from (select from Make where name = \"Jeep\")) where price > 70000)", new OCommandResultListener() {
                @Override
                public boolean result(Object o) {
                    ODocument doc = (ODocument) o;
                    System.out.println((String)doc.field("fullName"));
                    return true;
                }

                @Override
                public void end() {

                }
            }).execute();

            Thread.sleep(5000);
//            doc.delete();

            db.commit();
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.close();
        }

    }
}
