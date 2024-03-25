package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.*;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceTest {
    private Service service;
    private StudentXMLRepository studentXMLRepository;
    private TemaXMLRepository temaXMLRepository;
    private NotaXMLRepository notaXMLRepository;

    @BeforeEach
    void setUp(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        studentXMLRepository = new StudentXMLRepository(studentValidator, "studenti.xml");
        temaXMLRepository = new TemaXMLRepository(temaValidator, "teme.xml");
        notaXMLRepository = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @org.junit.jupiter.api.Test
    void test_save_valid_student() {
        assertEquals(service.saveStudent("17", "test1", 933), 1);
        studentXMLRepository.delete("17"); //delete test student
    }

    @org.junit.jupiter.api.Test
    void test_save_invalid_student() {
        assertEquals(service.saveStudent("-18", "test2", 933), 1);
        studentXMLRepository.delete("-18"); //delete in case it works
    }

    @org.junit.jupiter.api.Test
    public void test_add_duplicate_student(){
        int success = this.service.saveStudent("3", "William Koylong", 933);
        assertEquals(success, 0);

        success = this.service.saveStudent("3", "William Koylong", 933);
        assertEquals(success, 0);

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
        assertThrows(ValidationException.class, () -> this.service.saveStudent("", "Cazan e46", 111));
    }

    @org.junit.jupiter.api.Test
    public void test_add_student_with_null_id() {
        assertThrows(ValidationException.class, () -> this.service.saveStudent(null, "Cazan e39", 111));
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
        assertThrows(ValidationException.class, () -> this.service.saveStudent("12223", "", 223));

    }

    @org.junit.jupiter.api.Test
    public void test_add_student_with_null_name(){
        assertThrows(ValidationException.class, () -> this.service.saveStudent("12223", null, 223));
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
        assertThrows(ValidationException.class, () -> this.service.saveStudent("1234", "Dicky Moans", -6));
    }

    /**
     * test student email
     */
    @org.junit.jupiter.api.Test
    public void test_add_student_with_valid_email() {
        this.service.saveStudent("1111", "Kung Fu Tel", 334);
        Iterator<Student> students = this.service.findAllStudents().iterator();
        assertEquals(students.next().getID(), "11111");

        this.service.deleteStudent("1111");
    }

    @org.junit.jupiter.api.Test
    public void test_add_student_with_empty_email() {
        assertThrows(ValidationException.class, () -> this.service.saveStudent("1111", "Milion Prescurtat", 334));
    }

    @org.junit.jupiter.api.Test
    public void test_add_student_with_null_email() {
        assertThrows(ValidationException.class, () -> this.service.saveStudent("1111", "Moara Englishword", 334));
    }

    /**
     * BVA Test case
     */
    @org.junit.jupiter.api.Test
    public void test_add_student_group_lower_BVA_bound(){
        this.service.saveStudent("1", "Ben Dover", 0);
        Iterator<Student> students = this.service.findAllStudents().iterator();
        assertEquals(students.next().getID(), "1");

        this.service.deleteStudent("1");
    }
}