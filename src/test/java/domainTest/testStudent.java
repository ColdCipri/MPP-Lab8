package domainTest;

import domain.Student;

public class testStudent
{
    public void testStud()
    {
        Student student = new Student("123ab","Alin",1);
        //student.setId((long)10);
        assert (student.getClass() == Student.class);
        assert (student.getName().equals("Alin"));
        assert (student.getSerialNumber().equals("123ab"));
        assert (student.getGroup() == 1);
        //assert (student.getId() == (long)10);
    }
}
