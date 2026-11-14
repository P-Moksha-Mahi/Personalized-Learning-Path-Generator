
public class Student {

    private String name;
    private String goal;
    private String skillLevel;

    public Student(String name, String goal, String skillLevel) {
        this.name = name;
        this.goal = goal;
        this.skillLevel = skillLevel;
    }

    public String getName() {
        return name;
    }

    public String getGoal() {
        return goal;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void displayDetails() {
        System.out.println("Student Name: " + name);
        System.out.println("Learning Goal: " + goal);
        System.out.println("Skill Level: " + skillLevel);
    }
}
