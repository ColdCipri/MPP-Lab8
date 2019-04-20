package main;


import domain.AssignProblem;
import domain.Grades;

import domain.Problem;
import domain.Student;
import domain.validator.*;
import repository.Repository;
import repository.fileRepository.AssignFileRepository;
import repository.fileRepository.GradesFileRepository;
import repository.fileRepository.ProblemFileRepository;
import repository.xmlRepository.*;
import repository.*;
import repository.dbrepository.*;
import Service.AssignmentService;
import Service.GradeService;
import Service.ProblemService;
import Service.StudentService;
import ui.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
    @SuppressWarnings("all")
	public static void main(String[] argv)// throws IOException
    {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        Validator<Grades> gradesValidator = new GradeValidator();
        Validator<AssignProblem> assignProblemValidator = new AssignmentValidator();
        
        /* in memory Repo */
        Repository<Long, Student> studentRepo = new InMemoryRepository(studentValidator);
        Repository<Long, Problem> problemRepo = new InMemoryRepository(problemValidator);
        Repository<Long, Grades> gradesRepo = new InMemoryRepository(gradesValidator);
        Repository<Long, AssignProblem> assignRepo = new InMemoryRepository(assignProblemValidator);
		
		
        /* file Repo 
        Repository<Long, Student> studentRepo = new StudentFileRepository(studentValidator, "data/students.txt");
        Repository<Long, Problem> problemRepo = new ProblemFileRepository(problemValidator, "data/problems.txt");
        Repository<Long, Grades> gradesRepo = new GradesFileRepository(gradesValidator, "data/grades.txt");
        Repository<Long, AssignProblem> assignRepo = new AssignFileRepository(assignProblemValidator, "data/assignProblem.txt");
        */
        
        /* XML Repo
        Repository<Long, Student> studentRepo = new StudentXmlRepository(studentValidator, "data/students.xml");
        Repository<Long, Problem> problemRepo = new ProblemXmlRepository(problemValidator, "data/problems.xml");
        Repository<Long, Grades> gradesRepo = new GradeXmlRepository(gradesValidator, "data/grades.xml");
        Repository<Long, AssignProblem> assignRepo = new AssignProblemXmlRepository(assignProblemValidator, "data/assignProblem.xml"); */
        
        /* DataBase Repo 
        Repository<Long, Student> studentRepo = new StudentDbRepository(studentValidator);
        Repository<Long, Problem> problemRepo = new ProblemsDbRepository(problemValidator);
        Repository<Long, Grades> gradesRepo = new GradesDbRepository(gradesValidator);
        Repository<Long, AssignProblem> assignRepo = new AssignProblemDbRepository(assignProblemValidator);
         */
        
        StudentService studentService = new StudentService(studentRepo);
        ProblemService problemService = new ProblemService(problemRepo);
        GradeService gradeService = new GradeService(gradesRepo);
        AssignmentService assignmentService = new AssignmentService(assignRepo);

        //BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        //int connectAs = Integer.parseInt(bf.readLine());

        Console console = new Console(studentService, problemService, gradeService, assignmentService);
        console.runConsole();

        
    }
}
