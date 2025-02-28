DROP TABLE IF EXISTS Animal_Visits;
DROP TABLE  IF EXISTS Animals;
DROP TABLE  IF EXISTS Habitats;
DROP TABLE  IF EXISTS Visitors;


CREATE TABLE Habitats (
    Habitat_ID INT auto_increment PRIMARY KEY,
    Name VARCHAR(100),
    Type VARCHAR(100)
);

CREATE TABLE Visitors (
    Visitor_ID INT auto_increment PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Tickets INT
);

CREATE TABLE Animals (
    Animal_ID INT auto_increment PRIMARY KEY,
    Name VARCHAR(100),
    Species VARCHAR(100),
    Age INT,
    Habitat_ID INT,
    FOREIGN KEY (Habitat_ID) REFERENCES Habitats(Habitat_ID)
);

CREATE TABLE Animal_Visits (
    Id INT auto_increment PRIMARY KEY,
    Animal_ID INT,
    Visitor_ID INT,
    Visit_Date DATE,
    FOREIGN KEY (Animal_ID) REFERENCES Animals(Animal_ID),
    FOREIGN KEY (Visitor_ID) REFERENCES Visitors(Visitor_ID)
);

-- Inserting data into Habitats
INSERT INTO Habitats (Habitat_ID, Name, Type)
VALUES (1, 'Rainforest', 'Tropical'),
       (2, 'Savannah', 'Grassland'),
       (3, 'Aquarium', 'Marine');

-- Inserting data into Animals
INSERT INTO Animals (Animal_ID, Name, Species, Age, Habitat_ID)
VALUES (1, 'Leo', 'Lion', 5, 2),
       (2, 'Ellie', 'Elephant', 10, 2),
       (3, 'Nemo', 'Clownfish', 2, 3),
       (4, 'Bella', 'Parrot', 3, 1);

-- Inserting data into Visitors
INSERT INTO Visitors (Visitor_ID, Name, Email, Tickets)
VALUES (1, 'Alice Smith', 'alice.smith@example.com', 2),
       (2, 'Bob Brown', 'bob.brown@example.com', 4),
       (3, 'Charlie Green', 'charlie.green@example.com', 1);

-- Inserting data into Animal_Visits
INSERT INTO Animal_Visits (Animal_ID, Visitor_ID, Visit_Date)
VALUES (1, 1, '2023-11-13'),
       (2, 2, '2023-11-14'),
       (3, 3, '2023-11-15'),
       (4, 1, '2023-11-16');
