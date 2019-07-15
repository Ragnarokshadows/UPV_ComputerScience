package UniversityManagement;

public class Classroom{
    private Student [] students;
    private String group;
    private int floor;
    
    public Classroom(int numStudents, String clName, int clFloor){
        students = new Student[numStudents];
        group = clName;
        floor = clFloor;
    }
    
    public String getGroup(){return group;}
    public int getFloor(){return floor;}
    
    public void setGroup(String clName){group = clName;}
    public void setFloor(int clFloor){floor = clFloor;}
    
    public void resolution(){
        int i;
        int counter = 0;
        
        for (i = 0;i < students.length;i++){
            if (students[i] != null){
                if (!students[i].hasPassed()){
                    students[i] = null;
                    counter++;
                }
            }
        }
    }
    
    public Student searchStudent(String name){
        int i = 0;
        boolean end = false;
        
        while (i < students.length && end == false){
            if(students[i] != null){
                if (students[i].getName().equals(name)){
                    end = true;
                } 
                else{i++;}
            }
        }
        
        return students[i];
    }
    
    /*I have changed the parameter of the addStudent in order to have the
    marks of the students already initialized, I have talked with the group 
    in charge and they agreed this change*/
    public boolean addStudent(Student s){
        boolean res = false;
        int i = 0; 
        
        s.setGroup(group);
        
        while (i < students.length && res == false){
            if (students[i] == null){
                students[i] = s;
                res = true;
            }
            else{i++;}
        }
        
        return res;
    }
    
    public boolean removeStudent(String name){
        boolean res = false;
        int i = 0; 
        
        while (i < students.length && res == false){
            if (students[i].getName().equals(name)){
                students[i] = null;
                res = true;
            }
            else{i++;}
        }
        
        return res;
    }
    
    public String toString(){
        int i;
        String s = "Students in the classroom: " + "\n" + "\n";
        
        for (i = 0;i < students.length;i++){
            if (students[i] != null){
                s = s + students[i] + "\n" + "\n";
            }
        }
        
        return s;
    }
}