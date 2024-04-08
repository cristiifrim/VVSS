package service;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static service.ServiceTest.createAssignmentsXML;
import static service.ServiceTest.createStudentsXML;

public class BigBangIntegrationTest {
    private Service service;
    private StudentXMLRepository studentXMLRepository;
    private TemaXMLRepository temaXMLRepository;
    private NotaXMLRepository notaXMLRepository;

    @BeforeEach
    void setUp(){
        createStudentsXML();
        createAssignmentsXML();
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        studentXMLRepository = new StudentXMLRepository(studentValidator, "test_studenti.xml");
        temaXMLRepository = new TemaXMLRepository(temaValidator, "test_teme.xml");
        notaXMLRepository = new NotaXMLRepository(notaValidator, "test_note.xml");

        service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @AfterEach
    void removeXML() {
        new File("test_studenti.xml").delete();
        new File("test_teme.xml").delete();
        new File("test_note.xml").delete();
    }

    @Test
    public void test_save_student() {
        assertDoesNotThrow(() -> service.saveStudent("1", "IONEL", 935));

        Iterable<Student> students = studentXMLRepository.findAll();
        ArrayList<Student> studentList = new ArrayList<>();
        students.forEach(studentList::add);

        assertEquals(studentList.size(), 1);
        assertEquals(studentList.get(0).getID(), "1");
        assertEquals(studentList.get(0).getNume(), "IONEL");
        assertEquals(studentList.get(0).getGrupa(), 935);
    }

    @Test
    public void test_save_tema() {
        assertDoesNotThrow(() -> service.saveTema("1", "DESCRIPTION", 12, 1));


        Iterable<Tema> assignments = temaXMLRepository.findAll();
        ArrayList<Tema> assignmentList = new ArrayList<>();
        assignments.forEach(assignmentList::add);

        assertEquals(assignmentList.size(), 1);
        assertEquals(assignmentList.get(0).getID(), "1");
        assertEquals(assignmentList.get(0).getDescriere(), "DESCRIPTION");
        assertEquals(assignmentList.get(0).getDeadline(), 12);
        assertEquals(assignmentList.get(0).getStartline(), 1);
    }

    @Test
    public void test_fail_save_nota() {
        assertThrows(RuntimeException.class, () -> service.saveNota("1", "1", 10, 5, "SLAB"));
    }

    @Test
    public void test_all() {
        assertDoesNotThrow(() -> service.saveStudent("1", "IONEL", 935));
        assertDoesNotThrow(() -> service.saveTema("1", "DESCRIPTION", 12, 1));
        assertDoesNotThrow(() -> service.saveNota("1", "1", 6, 11, "SLAB"));

        Iterable<Student> students = studentXMLRepository.findAll();
        ArrayList<Student> studentList = new ArrayList<>();
        students.forEach(studentList::add);

        Iterable<Tema> assignments = temaXMLRepository.findAll();
        ArrayList<Tema> assignmentList = new ArrayList<>();
        assignments.forEach(assignmentList::add);

        Iterable<Nota> grades = notaXMLRepository.findAll();
        ArrayList<Nota> gradeList = new ArrayList<>();
        grades.forEach(gradeList::add);

        assertEquals(studentList.size(), 1);
        assertEquals(studentList.get(0).getID(), "1");
        assertEquals(studentList.get(0).getNume(), "IONEL");
        assertEquals(studentList.get(0).getGrupa(), 935);

        assertEquals(assignmentList.size(), 1);
        assertEquals(assignmentList.get(0).getID(), "1");
        assertEquals(assignmentList.get(0).getDescriere(), "DESCRIPTION");
        assertEquals(assignmentList.get(0).getDeadline(), 12);
        assertEquals(assignmentList.get(0).getStartline(), 1);

        assertEquals(gradeList.size(), 1);
        assertEquals(gradeList.get(0).getID(), new Pair<>("1", "1"));
        assertEquals(gradeList.get(0).getNota(), 8.5, 0.01);
        assertEquals(gradeList.get(0).getSaptamanaPredare(), 11);
        assertEquals(gradeList.get(0).getFeedback(), "SLAB");
    }
}