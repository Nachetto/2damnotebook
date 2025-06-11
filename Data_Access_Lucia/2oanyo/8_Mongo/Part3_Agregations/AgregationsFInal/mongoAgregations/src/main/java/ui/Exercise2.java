package ui;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;

public class Exercise2 {
    public static void main(String[] args) {
        MongoClient mongo = MongoClients.create("mongodb://informatica.wompwomp.com:2323");
        MongoDatabase db = mongo.getDatabase("IgnacioLlorente_company");

        MongoCollection<Document> employees = db.getCollection("Exercise2");

        // 1. Encontrar el empleado más joven
        System.out.println("1. Empleado más joven:");
        employees.aggregate(Arrays.asList(
                sort(new Document("age", 1)),
                limit(1)
        )).into(new ArrayList<>()).forEach(System.out::println);

        // 2. Calcular el salario promedio por departamento
        System.out.println("\n2. Salario promedio por departamento:");
        employees.aggregate(Arrays.asList(
                group("$department", avg("avgSalary", "$salary"))
        )).into(new ArrayList<>()).forEach(System.out::println);

        // 3. Encontrar el proyecto con más horas trabajadas en total
        System.out.println("\n3. Proyecto con más horas trabajadas:");
        employees.aggregate(Arrays.asList(
                unwind("$projects"),
                group("$projects.name", sum("totalHours", "$projects.hours")),
                sort(new Document("totalHours", -1)),
                limit(1)
        )).into(new ArrayList<>()).forEach(System.out::println);

        // 4. Contar cuántos empleados tienen la habilidad "Java"
        System.out.println("\n4. Número de empleados con habilidad 'Java':");
        employees.aggregate(Arrays.asList(
                match(new Document("skills", "Java")),
                count("java_employees")
        )).into(new ArrayList<>()).forEach(System.out::println);

        // 5. Encontrar el empleado que ha trabajado más horas en total
        System.out.println("\n5. Empleado con más horas trabajadas en total:");
        employees.aggregate(Arrays.asList(
                unwind("$projects"),
                group("$name", sum("totalHoursWorked", "$projects.hours")),
                sort(new Document("totalHoursWorked", -1)),
                limit(1)
        )).into(new ArrayList<>()).forEach(System.out::println);

        mongo.close();
    }
}