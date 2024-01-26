package main;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Coffee;
import model.Supplier;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Filters.eq;


public class TutInsert {

    public static void main(String[] args) {

        try (MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_coffeecompany");
            MongoCollection<Document> est = db.getCollection("suppliers");

            //First option: Never
//        est.insertOne(Document.parse("{ \"street\":\"blabla\",\"country\":\"USA\"}"));
//
//     Second option: Hardcode document and insert
//       Document d = new Document();
//       d.put("street","whatever");
//        d.put("town","whatever2");
//        d.put("country","Spain");
//        d.put("coffees", Arrays.asList(new Document()
//                .append("_id", 6000)
//                .append("cofName", "coffee3")
//                .append("price", 10.1),
//                new Document()
//                        .append("_id", 7000)
//                        .append("cofName", "coffee4")
//                        .append("price", 8.1)));
//
//        est.insertOne(d);

            // Third option: Use SupplierConverter class

//        Supplier s = Supplier.builder().street("street103").town("Boston")
//                .country("USA").pcode("123212")
//                .coffee(Coffee.builder()
//                        .idProd(9000)
//                        .cofName("Coffee 9")
//                        .price(15.00).build())
//                .coffee(Coffee.builder()
//                        .idProd(9000)
//                        .cofName("Coffee 9")
//                        .price(15.00).build())
//                .build();
//
//        SupplierConverter sc = new SupplierConverter();
//        Document d1 = sc.convertSupplierDocument(s);
//        est.insertOne(d1);
//        s.setSupp_id(d1.getObjectId("_id"));
//        System.out.println(s);

            // Insert Many

//        List<Document> documents = new ArrayList<>();
//        for (int i = 0; i < 5; i++)
//            documents.add(new Document("i", i));

//        InsertManyOptions options = new InsertManyOptions();
//        options.ordered(false);
//        est.insertMany(documents, options);

//Fourth option: Use Gson
//            Supplier s = Supplier.builder().street("street103").town("Boston")
//                    .country("USA").pcode("123212")
//                    .coffee(Coffee.builder()
//                            .idProd(2000)
//                            .cofName("Coffee 9")
//                            .price(15.00).build())
//                    .coffee(Coffee.builder()
//                            .idProd(9000)
//                            .cofName("Coffee 9")
//                            .price(15.00).build())
//                    .build();
//            Document document = Document.parse(new Gson().toJson(s));
//            est.insertOne(document);
//
////Add a list of coffees
//            List<Document> coffeeDocuments = new ArrayList<>();
//            for (Coffee coffee : s.getCoffees()) {
//                String coffeeJson = new Gson().toJson(coffee);
//                Document coffeeDocument = Document.parse(coffeeJson);
//                coffeeDocuments.add(coffeeDocument);
//            }
//        est.updateOne(
//                eq("_id", s.getSupp_id()),
//                push("coffees", coffeeDocuments));

//Add one coffee: In TutUpdate


//        }
//        catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
