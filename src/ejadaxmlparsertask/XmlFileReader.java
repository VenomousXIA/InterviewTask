package ejadaxmlparsertask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author sasuk
 */
public class XmlFileReader {

    private static XmlFileReader reader = null;

    private XmlFileReader() {
    }

    /**
     * If an instance of XmlFileReader was instantiated before return it
     * otherwise create a new instance (Singleton).
     *
     * @return XmlFileReader to read the student data from the given file.
     */
    public static XmlFileReader getInstance() {
        if (reader == null) {
            reader = new XmlFileReader();
        }
        return reader;
    }

    /**
     * Reads the students data from the XML file provided to it
     *
     * @param fileName file name of the XML file containing student data.
     * @return array of students found in the file after parsing XML.
     */
    public ArrayList<Student> readStudentsFromFile(String fileName) {
        File f = new File(fileName);
        ArrayList<Student> students = new ArrayList<>();
        XmlParser parser = XmlParser.getInstance();

        // reading student attributes
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String ename = "";
            String aname = "";
            String email = "";
            String phone = "";
            String address = "";
            int id = 1;

            while (br.ready()) {
                String input = br.readLine();

                if (input.contains("<ename>")) {
                    ename = parser.parse(input);
                } else if (input.contains("<aname>")) {
                    aname = parser.parse(input);
                } else if (input.contains("<email>")) {
                    email = parser.parse(input);
                } else if (input.contains("<phone>")) {
                    phone = parser.parse(input);
                } else if (input.contains("<address>")) {
                    address = parser.parse(input);
                } else if (input.contains("</student>")) {
                    Student student = new Student(id, ename, aname, email, phone, address);
                    parser.isValidStudent(student);
                    students.add(student);
                    id++;
                }
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }
}
