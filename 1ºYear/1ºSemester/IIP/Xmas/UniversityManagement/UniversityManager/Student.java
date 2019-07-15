package UniversityManagement;

public class Student{
    private double [] marks;
    private String group;
    private String name;
    
    public Student(int [] nmarks, String nname){
        int i;
        marks = new double [nmarks.length];
        
        name = nname;
        
        for (i = 0;i < nmarks.length;i++){
            marks[i] = nmarks[i];
        }
    }
    
    /*I have added this two methods to update the information of the group
    and to get the name of the student as there isn't other way to do it, I 
    have talked with the group and they agreed this changes*/
    public String getName(){return name;}
    public void setGroup(String ngroup){group = ngroup;}
    
    public void retakeExam(int index){
        if (marks[index] < 5){
            marks[index] = 5;
        }
    }
    
    public double average(){
        double res = 0;
        int i;
        
        for (i = 0;i < marks.length;i++){
            res = res + marks[i];
        }
        res = res / marks.length;
        
        return res;
    }
    
    public boolean hasPassed(){
        boolean res = true;
    
        if (average() < 5){res = false;}
        
        return res;
    }
    
    public String toString(){
        int i;
        String s = "Student: " + name + "\n" + "\n" + "Group: " + group +
        "\n" + "\n" + "Marks: ";
        
        for (i = 0;i < marks.length;i++){
            s = s + marks[i] + " ";
        }
        
        return s;
    }
}