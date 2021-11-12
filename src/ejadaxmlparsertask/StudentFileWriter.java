package ejadaxmlparsertask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author sasuk
 */
public class StudentFileWriter {

    private static StudentFileWriter writer = null;

    private StudentFileWriter() {
    }

    /**
     * If an instance of StudentFileWriter was instantiated before return it otherwise
     * create a new instance (Singleton).
     * 
     * @return StudentFileWritter to handle writing data to .TXT and .XLSX files.
     */
    public static StudentFileWriter getInstance() {
        if (writer == null) {
            writer = new StudentFileWriter();
        }
        return writer;
    }

    /**
     * Writes all students validation data to text file.
     * @param students Students to be written to text file.
     */
    public void writeValidationStatus(ArrayList<Student> students) {
        File f = new File("Validation Status.txt");
        XmlParser parser = XmlParser.getInstance();

        try {
            PrintWriter pw = new PrintWriter(f);

            for (Student student : students) {
                pw.println(parser.getValidationMessage(student));
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all valid student data to excel sheet file.
     * @param students Valid students to be written to excel sheet.
     */
    public void writeValidStudentsExcelFile(ArrayList<Student> students) {
        File f = new File("Valid Students.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Student Data");
        XSSFRow row;
        Cell cell;

        String[] cellNames = {"Student ID", "English Name", "Arabic Name", "Email", "Phone", "Address"};
        row = sheet.createRow(0);
        for (int i = 0; i < cellNames.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(cellNames[i]);
        }

        int rowID = 1;
        for (Student student : students) {
            if (student.getValid()) {
                int cellID = 0;
                row = sheet.createRow(rowID++);
                for (String value : student.toStringArray()) {
                    cell = row.createCell(cellID++);
                    cell.setCellValue(value);
                }
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(f);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
