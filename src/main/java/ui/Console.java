package ui;

import Service.AssignmentService;
import Service.ProblemService;
import Service.StudentService;
import Service.GradeService;
import domain.AssignProblem;
import domain.Discipline;
import domain.Grades;
import domain.Problem;
import domain.Student;
import domain.validator.ValidatorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("all")
public class Console {
    private StudentService studentService;
    private ProblemService problemService;
    private GradeService gradeService;
    private AssignmentService asService;

    public Console(StudentService studentService, ProblemService problemService, GradeService gradeService, AssignmentService asService) {
        this.studentService = studentService;
        this.problemService = problemService;
        this.gradeService = gradeService;
        this.asService = asService;
    }

    public int getOption()
    {
        System.out.println("Input option: ");
        Scanner sc = new Scanner(System.in);
        int opt = 0;
        opt = sc.nextInt();
        return opt;
    }

    public void printMenu()
    {
        System.out.println("0. Exit");
        System.out.println("1. Add Student");
        System.out.println("2. Print Students");
        System.out.println("3. Add Problem");
        System.out.println("4. Print problems");
        System.out.println("5. Add Grade");
        System.out.println("6. Print Grade");
        System.out.println("7. Get most assigned problems");
        System.out.println("8. Assign Problems To Students");
        System.out.println("9. Print Assigned Problems");
        //System.out.println("6. Get the problems with the given description");
        //System.out.println("7. Filter students by name");
        //System.out.println("8. Filter problems by name");
        //System.out.println("9. Get the number of problems by difficulty");
    }

    public void runConsole()
    {
        int opt = -1;
        while (opt != 0) {
            this.printMenu();
            opt = this.getOption();
            switch (opt) {
                case 0:
                    break;
                case 1:
                    addStudents();
                    break;
                case 2:
                    printAllStudents();
                    break;
                case 3:
                    addProblems();
                    break;
                case 4:
                    printAllProblems();
                    break;
                case 5:
                    addGrades();
                    break;
                case 6:
                	printAllGrades();
                    break;
                case 7:
                    GetMostAssignedProblems();
                    break;
                case 8:
                    addAssignments();
                    break;
                case 9:
                    printAllAss();
                    break;
                    /*case 7:
                    filterStudents();
                    break;
                case 8:
                    filterProblems();
                    break;
                case 9:
                    GetProblemsByDifficulty();
                    break;*/
            }
        }
        //filterStudents();
    }

    private void filterStudents() {
        System.out.println("Input name:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String name = bufferRead.readLine();

            Set<Student> students = studentService.filterStudentsByName(name);
            students.stream().forEach(System.out::println);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void filterProblems() {
        System.out.println("Input name:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String name = bufferRead.readLine();

            Set<Problem> problems = problemService.filterProblemByName(name);
            problems.stream().forEach(System.out::println);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void getProblemsByDescription(){
        System.out.println("Input description: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            String s = bufferRead.readLine();

            Set<Problem> problems = problemService.filterProblemByDescription(s);
            problems.stream().forEach(System.out::println);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void GetProblemsByDifficulty()
    {
        System.out.println("Input difficulty: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            int number = Integer.parseInt(bufferRead.readLine());

            Set<Problem> problems = problemService.filterProblemByDifficulty(number);
            System.out.println("Problem count: " + problems.stream().count());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void GetMostAssignedProblems()
    {
        try
        {
            Optional<AssignProblem> ap = asService.filterAssignments();
            System.out.println("Most assigned problem: " + ap.get().toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void printAllStudents() {
        Set<Student> students = studentService.getAllStudents();
        students.stream().forEach(System.out::println);
    }

    private void printAllProblems()
    {
        Set<Problem> problems = problemService.getAllProblems();
        problems.stream().forEach(System.out::println);
    }
    
    private void printAllGrades()
    {
        Set<Grades> grades = gradeService.getAllGrades();
        grades.stream().forEach(System.out::println);
    }
    
    private void printAllAss()
    {
        Set<AssignProblem> ass = asService.getAllStudents();
        ass.stream().forEach(System.out::println);
    }

    private void addStudents() {
        while (true) {
            Student student = readStudent();
            if (student == null || student.getId() == null) {
                break;
            }
            try {
                studentService.addStudent(student);
                break;
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private void addProblems()
    {
        while(true)
        {
            Problem problem = readProblem();
            if (problem == null || problem.getId() == null)
            {
                break;
            }
            try
            {
                problemService.addProblem(problem);
                break;
            }
            catch (ValidatorException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addGrades()
    {
        while(true)
        {
            Grades grade = readGrade();
            if (grade == null || grade.GetGrade() <= 0)
            {
                break;
            }
            try
            {
                gradeService.addGrade(grade);
                break;
            }
            catch (ValidatorException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addAssignments()
    {
        while (true)
        {
            AssignProblem ap = readAP();
            if (ap == null || ap.getProblemId()<= 0)
            {
                break;
            }
            try
            {
                asService.addAP(ap);
                break;
            }
            catch (ValidatorException e)
            {
                e.printStackTrace();
            }
        }
    }
    

    private Student readStudent() {
        System.out.println("Read student {id,serialNumber, name, group}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Id");
            Long id = Long.valueOf(bufferRead.readLine());// ...
            System.out.println("SerialNumber");
            String serialNumber = bufferRead.readLine();
            System.out.println("Name");
            String name = bufferRead.readLine();
            System.out.println("Group");
            int group = Integer.parseInt(bufferRead.readLine());// ...

            Student student = new Student(serialNumber, name, group);
           	student.setId(id);

            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Problem readProblem()
    {
        System.out.println("Read problem {id, description, name, difficulty}");

        BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            System.out.println("Id");
            Long id = Long.valueOf(bufferedRead.readLine());
            System.out.println("Problem description");
            String problemDescription = bufferedRead.readLine();
            System.out.println("Name");
            String name = bufferedRead.readLine();
            System.out.println("Number");
            int number = Integer.parseInt(bufferedRead.readLine());

            Problem problem = new Problem(problemDescription, name, number);
            problem.setId(id);
            return problem;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private Grades readGrade()
    {
        System.out.println("Read grade {id, problemId, studentId, grade}");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            System.out.println("Id:");
            Long id = Long.valueOf(bufferedReader.readLine());
            System.out.println("problem:");
            int pid = Integer.valueOf(bufferedReader.readLine());
            System.out.println("student:");
            int sid = Integer.valueOf(bufferedReader.readLine());
            System.out.println("grade:");
            int grade = Integer.valueOf(bufferedReader.readLine());

            Grades gradeObj = new Grades(pid, sid, grade);
            gradeObj.setId(id);

            return gradeObj;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private AssignProblem readAP()
    {
        System.out.println("Read assignment {id, problem Id, studentId}");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            System.out.println("Id:");
            Long id = Long.valueOf(bufferedReader.readLine());
            System.out.println("problem:");
            int pid = Integer.valueOf(bufferedReader.readLine());
            System.out.println("student:");
            int sid = Integer.valueOf(bufferedReader.readLine());

            AssignProblem apObj = new AssignProblem(pid, sid);
            apObj.setId(id);

            return apObj;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
