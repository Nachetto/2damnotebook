package org.example.ui;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.model.errors.GymError;
import org.example.services.ClientService;
import org.example.services.impl.SubscriptionsServiceImpl;

import java.util.Scanner;

public class MainEx2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ClientService clientService = container.select(ClientService.class).get();
        Either<GymError, Integer> eitherfalse = clientService.delete(1, false);
        Scanner sc = new Scanner(System.in);

        if (eitherfalse.isRight()) {
            System.out.println("Client deleted");
        } else if (eitherfalse.isLeft()) {
            if (eitherfalse.getLeft().getMessage().equals("The client has subscriptions")) {
                System.out.println("The client has subscriptions, do you want to delete them? (Y/N)");
                String answer = sc.nextLine();
                if (answer.equals("Y")) {
                    Either<GymError, Integer> eithertrue = clientService.delete(1, true);
                    if (eithertrue.isRight()) {
                        System.out.println("Client deleted");
                    } else {
                        System.out.println(eithertrue.getLeft().getMessage());
                    }
                } else {
                    System.out.println("You answered no.");
                }

            } else {
                System.out.println(eitherfalse.getLeft());
            }

        }
    }
}