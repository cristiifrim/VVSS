package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}