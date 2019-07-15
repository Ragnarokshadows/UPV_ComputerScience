package UniversityManagement;

public class UniversityManager{
    public static void main(String [] args){
        Classroom ara = new Classroom(25, "ARA", 1);
        int [] marksOne = {9,0};
        Student one = new Student(marksOne, "Stéphane");
        int [] marksTwo = {8,9};
        Student two = new Student(marksTwo, "Jorge");
        int [] marksThree = {4,3};
        Student three = new Student(marksThree, "Rafa");
        
        ara.addStudent(one);
        ara.addStudent(two);
        ara.addStudent(three);
        
        System.out.println(ara);
        
        ara.resolution();
        
        System.out.println("Resolution: " + "\n" + "\n" + ara);
    }
}