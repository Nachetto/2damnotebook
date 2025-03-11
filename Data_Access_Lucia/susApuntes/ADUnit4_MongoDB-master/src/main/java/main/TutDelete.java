package main;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Coffee;
import model.Supplier;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Updates.*;


public class TutDelete {


    public static void main(String[] args) {

        try (MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.wompwomp.com:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_coffeecompany");
            MongoCollection<Document> est = db.getCollection("suppliers");

//            System.out.println(
//                    est.deleteOne(eq("street", "street103")).getDeletedCount());
//
//        System.out.println(
//                est.deleteMany(eq("street", "street103")).getDeletedCount());
//
//        System.out.println(
//                est.deleteOne(eq("_id", new ObjectId("65a7b5998949bca214b9dfa1"))).getDeletedCount());

//Delete one coffee: Create the document with the coffee to be deleted
//            Document docCoffee = Document.parse(new Gson().toJson(new Coffee(2000, "Coffee 9", 15.00)));
//
//            //Delete one coffee
//            System.out.println(est.updateOne(
//                    eq("_id", new ObjectId("65abb8172d02f7275a6e870b")),
//                    pull("coffees",docCoffee)).getModifiedCount());

//Delete all coffees
//        List<Document> ldoc=new ArrayList<>();
//             est.updateOne(
//                eq("_id", new ObjectId("65abb1c210ab216ee9bbd2cc")),
//                set("coffees",ldoc));
        }

    }
}

