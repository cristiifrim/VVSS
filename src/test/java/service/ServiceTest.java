package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    private Service service;
    private StudentXMLRepository studentXMLRepository;
    private TemaXMLRepository temaXMLRepository;
    private NotaXMLRepository notaXMLRepository;

    public static void createStudentsXML() {
        File xml = new File("test_studenti.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createAssignmentsXML() {
        File xml = new File("test_teme.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createGradesXML() {
        File xml = new File("test_note.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp(){
        createStudentsXML();
        createAssignmentsXML();
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        studentXMLRepository = new StudentXMLRepository(studentValidator, "test_studenti.xml");
        temaXMLRepository = new TemaXMLRepository(temaValidator, "test_teme.xml");
        notaXMLRepository = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @AfterEach
    void removeXML() {
        new File("test_studenti.xml").delete();
        new File("test_note.xml").delete();
    }

    @org.junit.jupiter.api.Test
    void test_save_valid_student() {
        assertEquals(service.saveStudent("17", "test1", 933), 0);
        studentXMLRepository.delete("17"); //delete test student
    }

    @org.junit.jupiter.api.Test
    void test_save_invalid_student() {
        assertEquals(service.saveStudent("-18", "test2", 933), 0);
        studentXMLRepository.delete("-18"); //delete in case it works
    }

    @org.junit.jupiter.api.Test
    public void test_add_duplicate_student(){
        int success = this.service.saveStudent("3", "William Koylong", 933);
        assertEquals(success, 0);

        success = this.service.saveStudent("3", "William Koylong", 933);
        assertEquals(success, 1);

        this.service.deleteStudent("3");
    }

    @org.junit.jupiter.api.Test
    public void test_add_non_duplicate_student(){
        this.service.saveStudent("11", "Emi Lingura", 935);
        this.service.saveStudent("111", "Emi Lingura", 935);

        Iterator<Student> students = this.service.findAllStudents().iterator();

        assertEquals(students.next().getID(), "11");
        assertEquals(students.next().getID(), "111");

        this.service.deleteStudent("11");
        this.service.deleteStudent("111");
    }

    /**
     * test Student id
     */
    @org.junit.jupiter.api.Test
    public void test_add_student_with_valid_id() {
        this.service.saveStudent("2345", "Linjon Kun", 111);
        Iterator<Student> students = this.service.findAllStudents().iterator();
        assertEquals(students.next().getID(), "2345");

        this.service.deleteStudent("2345");
    }

    @org.junit.jupiter.api.Test
    public void test_add_student_with_empty_id() {
        assertEquals(this.service.saveStudent("", "Cazan e46", 111), 0);
    }

    @org.junit.jupiter.api.Test
    public void test_add_student_with_null_id() {
        assertEquals(this.service.saveStudent(null, "Cazan e39", 111), 0);
    }

    /**
     * test student name
     */
    @org.junit.jupiter.api.Test
    public void test_add_student_with_valid_name(){
        this.service.saveStudent("12223", "Mike Oxlong", 223);
        Iterator<Student> students = this.service.findAllStudents().iterator();
        assertEquals(students.next().getID(), "12223");

        this.service.deleteStudent("12223");
    }

    @org.junit.jupiter.api.Test
    public void test_add_student_with_empty_name(){
        assertEquals(this.service.saveStudent("12223", "", 223), 0);

    }

    @org.junit.jupiter.api.Test
    public void test_add_student_with_null_name(){
        assertEquals(this.service.saveStudent("12223", null, 223), 0);
    }


    /**
     * test student group
     */
    @org.junit.jupiter.api.Test
    public void test_add_student_from_valid_group() {
        this.service.saveStudent("1234", "Mike Oxbig", 844);;
        Iterator<Student> students = this.service.findAllStudents().iterator();
        assertEquals(students.next().getID(), "1234");

        this.service.deleteStudent("1234");
    }

    @org.junit.jupiter.api.Test
    public void test_add_student_from_invalid_group() {
        assertEquals(this.service.saveStudent("1234", "Dicky Moans", -6), 0);
    }

    /**
     * BVA Test case
     */
    @org.junit.jupiter.api.Test
    public void test_add_student_group_lower_BVA_bound(){
        this.service.saveStudent("1", "Ben Dover", 111);
        Iterator<Student> students = this.service.findAllStudents().iterator();
        assertEquals(students.next().getID(), "1");

        this.service.deleteStudent("1");
    }

    /**
     * Test add assignments
     */
    @org.junit.jupiter.api.Test
    public void test_save_tema_valid_identifier()
    {
        assertEquals(this.service.saveTema("1", "Web API an controller", 12, 10), 0);
    }

    @org.junit.jupiter.api.Test
    public void test_save_tema_invalid_identifier()
    {
        assertEquals(this.service.saveTema(null, "Web API an controller", 12, 10), 1);
    }

    @Test
    public void test_save_tema_valid_entity() {
        assertDoesNotThrow(() -> service.saveTema("1", "description", 14, 1));

        Tema addedAssignment = service.findAllTeme().iterator().next();
        assertNotNull(addedAssignment);

        assertEquals(addedAssignment.getID(), "1");
        assertEquals(addedAssignment.getDescriere(), "description");
        assertEquals(addedAssignment.getDeadline(), 14);
        assertEquals(addedAssignment.getStartline(), 1);
    }

    @Test
    public void test_tema_fail_invalid_id() {
        assertThrows(ValidationException.class, () -> service.saveTema(null, "description", 14, 1), "ID invalid! \n");
    }

    @Test
    public void test_tema_fail_invalid_description() {
        assertThrows(ValidationException.class, () -> service.saveTema("1", null, 14, 1), "Descriere invalida!\n");
    }

    @Test
    public void test_tema_fail_invalid_deadline() {
        assertThrows(ValidationException.class, () -> service.saveTema("1", "description", 0, 1), "Deadline invalid!\n");
    }

    @Test
    public void test_tema_fail_invalid_startline() {
        assertThrows(ValidationException.class, () -> service.saveTema("1", "description", 14, 0), "Data de primire invalida!\n");
    }

    @Test
    public void test_tema_fail_entity_with_id_already_present() {
        assertThrows(ValidationException.class, () -> {
            service.saveTema("1", "description", 14, 1);
            service.saveTema("1", "Another Description", 14, 1);
        });
    }
}