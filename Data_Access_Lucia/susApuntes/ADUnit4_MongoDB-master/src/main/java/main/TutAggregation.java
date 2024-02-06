/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import org.bson.BsonNull;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static java.util.Arrays.asList;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class TutAggregation {

    public static void main(String[] args) {
        MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("lucia");
        MongoCollection<Document> col = db.getCollection("zipcodes");

//MATCH
        //zipcodes from "CUSHMAN"
        // Mongo shell
        //db.zipcodes.aggregate({ $match: { city: "CUSHMAN" }})

//        //First option
        List<Document> list = new ArrayList<>();
        list.add(Document.parse("{ $match: { city: \"CUSHMAN\" } }"));
        col.aggregate(list).map(document -> document.getString("_id")+" belongs to CUSHMAN city").into(new ArrayList<>()).forEach(System.out::println);
//
//        //Second option
//        col.aggregate(Arrays.asList(
//                match(eq("city", "CUSHMAN"))))
//                        .into(new ArrayList<>()).forEach(document -> System.out.println(document.get("_id")+" belongs to CUSHMAN city"));
//
////AND
        // Zipcodes with population between 10000 to 20000
        // Mongo shell
        //db.zipcodes.aggregate({ $match: { $and: [ { "pop": { $gt: 10000, $lt: 20000 } } ] } } )

//        col.aggregate(Arrays.asList(
//                match(and(gt("pop", 10000),lte("pop",20000)))))
//                .into(new ArrayList<>()).forEach(System.out::println);


//PROJECT
        //Indianapolis zipcodes population
        //Mongo shell
        //db.zipcodes.aggregate({ $match: {city: "INDIANAPOLIS"}},
        //    {$project: {_id:0, pop:1}})

//        List<Document> list2 = new ArrayList<>();
//        list2.add(Document.parse("{ $match: {city: \"INDIANAPOLIS\"}}"));
//        list2.add(Document.parse("{$project: {pop:1}}"));
//        col.aggregate(list2).map(document -> document.getInteger("pop")+": population of zipcode "+ document.getString("_id")+" from INDIANAPOLIS").into(new ArrayList<>()).forEach(System.out::println);
//
//        col.aggregate(Arrays.asList(
//                        match(eq("city", "INDIANAPOLIS")),
//                        project(fields(include("pop")))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.getInteger("pop")+": population of zipcode "+ document.getString("_id")+" from INDIANAPOLIS"));


// REGEX
        //Cities with FALLS in their name
        //Mongo shell
        //{$match: {city: {$regex: "FALLS" }}},
        //{$project: {city:1, state:1, _id:0}}

//        col.aggregate(Arrays.asList(
//                        match(regex("city","FALLS")),
//                        project(fields(include("city","state"),exclude("_id")))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));

//ADDFIELDS
      //Zipcodes at X-location from -73 to -74
//        {$addFields: {locX: {$arrayElemAt:["$loc", 0]}}},{$match: {
//            $and: [ { "locX": { $gt: -74, $lte: -73 } } ]}}


//COUNT
        // Number of zipcodes with population between 10000 to 20000
        // Mongo shell
        //db.zipcodes.aggregate({ $match: { $and: [ { "pop": { $gt: 10000, $lt: 20000 } } ] } },
        // {$count: "MediumPopZipcode"})

//        col.aggregate(Arrays.asList(
//                match(and(gt("pop", 10000),lte("pop",20000))),
//                count("MediumZipCode")))
//                .into(new ArrayList<>()).forEach(System.out::println);

// SORT
        //list of all zipcodes ordered by state
        //{$sort: {state:1}}
//        col.aggregate(
//                Arrays.asList(
//                        sort(ascending("state"))))
//                        .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));

// SORT, LIMIT
        //ZipCode with the highest population
        //{$sort: {pop:-1}},
        //{$limit:1}

//        col.aggregate(
//                Arrays.asList(
//                        sort(ascending("state")),
//                        limit(1)))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));

//GROUP

        //Total population by state
//        Mongo shell
//        { $group: { _id: "$state", totalPop: { $sum: "$pop" } } }

//        col.aggregate(Arrays.asList(
//                        group("$state",sum("totalPop", "$pop"))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));

        //Population of the different cities of the state of NY
        //Mongo shell
//        { $match: { state: "NY" } },
//        { $group: { _id: "$city", total: { $sum: "$pop" } } }


//        col.aggregate(Arrays.asList(
//                        match(eq("state", "NY")),
//                        group("$city",sum("total", "$pop"))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));

//        Number of zipcodes of NewYork (Can also be done with count)
//        { $match: { city: "NEW YORK" } },
//        { $group: { _id: "$city", total: { $sum: 1 } } }

//        col.aggregate(Arrays.asList(
//                        match(eq("city", "NEW YORK")),
//                        group(new BsonNull(),sum("total", 1))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));



//        City with the highest population, specifying the state
//        { $group: { _id: { state: "$state", city: "$city" }, cityPop: { $sum: "$pop" } } },
//        {$sort: {cityPop:-1}},
//        {$limit:1}

//        col.aggregate(Arrays.asList(
//                        group((new Document("state", "$state")
//                .append("city", "$city")),sum("totalPop", "$pop")),
//                        sort(descending("totalPop")),
//                        limit(1)))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));

//        all states with total population greater than 10 million
//        Mongo shell
//        { $group: { _id: "$state", totalPop: { $sum: "$pop" } } },
//        { $match: { totalPop: { $gte: 10000000 } } }

//        col.aggregate(Arrays.asList(
//                        group("$state",sum("totalPop", "$pop")),
//                        match(gte("totalPop", 10000000))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));

//        Average City Population by State
//        Mongo shell
//        { $group: { _id: { state: "$state", city: "$city" }, cityPop: { $sum: "$pop" } } },
//        { $group: { _id: "$_id.state", avgCityPop: { $avg: "$cityPop" } } }

//        col.aggregate(Arrays.asList(
//                        group(fields(eq("state","$state"), eq("city","$city")),sum("cityPop", "$pop")),
//                        group("$_id.state",avg("avgCityPop", "$cityPop")),
//                        sort(ascending("_id"))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));


// UNWIND
        //Cities at latitude -72
        // db.zipcodes.aggregate({$unwind: "$loc"},
        // { $match: { $and: [ { "loc": { $gt: -73, $lte: -72 } } ] } },
        // {$project: {city:1, state:1, loc:1, _id:0}} )

//        col.aggregate(Arrays.asList(
//                        unwind("$loc"),
//                        match(and(gt("loc", -73),lte("loc",-72))),
//                        project(fields(include("city","state","loc"),exclude("_id")))))
//                .into(new ArrayList<>()).forEach(System.out::println);

//UNWIND & COUNT
        // Number of cities at latitude -72
//        db.zipcodes.aggregate({$unwind: "$loc"},
//        db.zipcodes.aggregate({$unwind: "$loc"},
//        { $match: { $and: [ { "loc": { $gt: -73, $lte: -72 } } ] } },
//        {$count:"Loc72" } )

//                col.aggregate(Arrays.asList(
//                        unwind("$loc"),
//                        match(and(gt("loc", -73),lte("loc",-72))),
//                        count("Loc72")))
//                .into(new ArrayList<>()).forEach(System.out::println);


//UNWIND & ADDFIELDS
        //Name of the most expensive coffee

//        {$unwind: "$coffees"},
//        {$sort: {"coffees.price":-1}},
//        {$limit: 1},
//        {$addFields: {coffeeName: "$coffees.cofName"}},
//        {$addFields: {coffeePrice: "$coffees.price"}},
//        {$project: {coffeeName:1, coffeePrice:1, _id:0}}

//        MongoCollection<Document> colSup = db.getCollection("coffeeSupplier");
//                colSup.aggregate(Arrays.asList(
//                        unwind("$coffees"),
//                        sort(descending("coffees.price")),
//                        limit(1),
//                        addFields(new Field("coffeeName","$coffees.cofName")),
//                        addFields(new Field("coffeePrice","$coffees.price")),
//                        project(fields(include("coffeeName", "coffeePrice")))))
//                .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));


//        {$match: {$expr:{$eq:[{$month : {$toDate: "$dtstart"}}, 1]}}},
//        {$project: {title:1, _id:0}}
        MongoCollection<Document> colMad = db.getCollection("madrid");
               colMad.aggregate(Arrays.asList(match(eq("date('$dtstart')",1))))
                       .into(new ArrayList<>()).forEach(document -> System.out.println(document.toString()));
    }

}
