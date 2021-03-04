import Test1.Client;
import Test1.Server;
import jdk.jfr.Event;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.EventObject;

public class main {

    public static void main(String[] args) throws ParseException {
        /*
        JSONObject obj = new JSONObject();
        obj.put("Name", "Mathias");
        obj.put("Age", 21);

        System.out.println(obj.toString());

        JSONParser parser = new JSONParser();

        JSONObject obj2 = (JSONObject) parser.parse(obj.toString());

        System.out.println(obj2);

         */


    }

    public JSONObject createJSON(StudentInfo si){
        StudentInfo student = new StudentInfo("Mathias", 1);
        JSONObject studentObj = new JSONObject();


        return studentObj;
    }

    private class StudentInfo {

        private String name;
        private int id;

        public StudentInfo(String studentName, int studentId){
            name = studentName;
            id = studentId;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }
}
