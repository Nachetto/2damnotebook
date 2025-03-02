package common;

public class Constants {
    public static final String FILE_PATH = "config/config.properties";
    public static final String PATH_DB_URL = "dbUrl";
    public static final String PATH_DB_USER = "root";
    public static final String PATH_DB_PASSWORD = "1234";
    public static final String PATH_DRIVER = "dbDriver";
    public static final String PATH_VISITS_XML = "pathVisitsXml";

    public static final String ADD_VISIT_QUERY = "INSERT INTO Animal_Visits (Animal_ID,Visitor_ID,Visit_Date) values (?,?,?);";
    public static final String ADD_VISITOR_QUERY = "INSERT INTO Visitors (Name,Email,Tickets) values (?,?,?);";
    public static final String CHECK_VISITS_QUERY = "SELECT * from Animal_Visits where Animal_ID = ?;";
    public static final String DELETE_ANIMAL_VISITS_QUERY = "DELETE from Animal_Visits where Animal_ID = ? ;";
    public static final String DELETE_ANIMALS_QUERY = "DELETE from Animals where Animal_ID= ?";
    public static final String GET_ANIMAL_FROM_USERNAME_QUERY = "SELECT * FROM Animals WHERE Name=?";

    private Constants(){}
}
