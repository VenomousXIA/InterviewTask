package ejadaxmlparsertask;

import java.util.ArrayList;
/**
 *
 * @author sasuk
 */
public class EjadaXmlParserTask {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XmlFileReader reader = XmlFileReader.getInstance();
        StudentFileWriter writer = StudentFileWriter.getInstance();
        
        ArrayList<Student> students = reader.readStudentsFromFile("student[417].xml");
        writer.writeValidationStatus(students);
        writer.writeValidStudentsExcelFile(students);
    }

}
