package main;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Supplier;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutUpdate {

  public static void main(String[] args) {
//    MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.wompwomp.com:2323");
//
//    MongoDatabase db = mongo.getDatabase("lucia");
//    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
//            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
//
//    MongoCollection<Supplier> est = db.getCollection("coffeeSupplier", Supplier.class)
//            .withCodecRegistry(pojoCodecRegistry);
    try (MongoClient mongo = MongoClients.create("mongodb://informatica.wompwomp.com:2323")) {
      MongoDatabase db = mongo.getDatabase("luciasanmiguel_coffeecompany");
      MongoCollection<Document> est = db.getCollection("suppliers");

//      System.out.println(est.updateOne(
//              eq("country", "Spain"),
//              set("pcode", "111")).getModifiedCount());

//      System.out.println(est.updateOne(
//              eq("country", "Spain"),
//              set("coffees.0.price", "11.34")).getModifiedCount());
//
//      System.out.println(est.updateOne(
//              eq("coffees._id", 1000),
//              set("coffees.$.cofName", "newName")).getModifiedCount());


      // Update all prices for coffees with supplier id --- Does not work because Mongo does not support
      // the positional operator $ in nested arrays - It has to be done one by one with the coffee Id
//    System.out.println(est.updateMany(
//            eq(eq("_id", new ObjectId("65a7b5998949bca214b9df9f"))),
//            set("coffees.$.price","15.00")).getModifiedCount());


//Add one coffee to the list

//      System.out.println(est.updateOne(
//                      eq("street", "street103"),
//                      push("coffees",
//                              new Document("_id", 7000).append("cofName", "pepe").append("price", 10.10)))
//              .getModifiedCount());
//
// Remove coffees from the list
//      System.out.println(est.updateMany(
//                      eq("street", "street103"),
//                      pull("coffees",
//                              eq("price", 11.10)))
//              .getModifiedCount());
    }
    }
}
