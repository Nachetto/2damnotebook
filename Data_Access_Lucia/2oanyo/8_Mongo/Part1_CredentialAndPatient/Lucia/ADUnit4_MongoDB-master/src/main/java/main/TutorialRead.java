package main;

import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import model.Supplier;
import model.SupplierConverter;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutorialRead {


    public static void main(String[] args) {

//Get suppliers
//  First option: Get suppliers from document and print data (no mapping)
//        try (MongoClient mongo = MongoClients.create("mongodb://informatica.wompwomp.com:2323")) {
//
//            MongoDatabase db = mongo.getDatabase("luciasanmiguel_coffeecompany");
//
//            MongoCollection<Document> est = db.getCollection("suppliers");
//            List<Document> suppliers = new ArrayList<>();
//            System.out.println("Get document suppliers");
//            est.find().into(suppliers).forEach(System.out::println);
//        }
//
////  Second option: Get suppliers from document and store it into a Supplier list with a Converter class
//        try (MongoClient mongo = MongoClients.create("mongodb://informatica.wompwomp.com:2323")) {
//
//            MongoDatabase db = mongo.getDatabase("luciasanmiguel_coffeecompany");
//
//            MongoCollection<Document> est = db.getCollection("suppliers");
//            SupplierConverter sc = new SupplierConverter();
//            List<Document> suppliers = new ArrayList<>();
//            System.out.println("Get suppliers with Converter class");
//            est.find().into(suppliers).stream().map(document -> sc.convertDocumentSupplier(document)).forEach(System.out::println);
//        }
//
//  Third option: Get suppliers from document and store it into a Supplier list with CodecRegistry
//        try (MongoClient mongo = MongoClients.create("mongodb://informatica.wompwomp.com:2323")) {
//            MongoDatabase db = mongo.getDatabase("luciasanmiguel_coffeecompany");
//            System.out.println("Get suppliers with CodecRegistry");
//            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
//                fromProviders(PojoCodecProvider.builder()
//                        .automatic(true).build()));
//            MongoCollection<Supplier> col = db.getCollection("suppliers", Supplier.class).withCodecRegistry(pojoCodecRegistry);
//
//            FindIterable<Supplier> suppliers2 = col.find();
//            for (Supplier supplier : suppliers2) {
//                System.out.println(supplier);
//        }
//        }

// Fourth option: Get suppliers from document and store it into a Supplier list with Gson
        try (MongoClient mongo = MongoClients.create("mongodb://informatica.wompwomp.com:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_coffeecompany");
            MongoCollection<Document> est = db.getCollection("suppliers");
            List<Supplier> suppliersList = new ArrayList<>();
            List<Document> documents = est.find().into(new ArrayList<>());
            for (Document supplier : documents) {
               suppliersList.add(new Gson().fromJson(supplier.toJson(), Supplier.class));
           }
        System.out.println(suppliersList);
        }

// Get all coffees
//        try (MongoClient mongo = MongoClients.create("mongodb://informatica.wompwomp.com:2323")) {
//            MongoDatabase db = mongo.getDatabase("luciasanmiguel_coffeecompany");
//            MongoCollection<Document> est = db.getCollection("suppliers");
//            List<Coffee> coffeesList = new ArrayList<>();
//            List<Document> documents = est.find()
//                    .projection(fields(excludeId(), include("coffees")))
//                    .into(new ArrayList<>());
//            for (Document document : documents) {
//                List<Document> documentCoffees = (List<Document>) document.get("coffees");
//                for (Document coffee : documentCoffees) {
//                    coffeesList.add(new Gson().fromJson(coffee.toJson(), Coffee.class));
//                }
//
//            }
//            System.out.println(coffeesList);
//        }
    }
}


