package osmandroid.venturesity.helloworld3o;

public class User {
    String name,age,weight,otherd;

    public User()
    {
    }

    public User(String name, String age, String weight, String otherd) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.otherd = otherd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOtherd() {
        return otherd;
    }

    public void setOtherd(String otherd) {
        this.otherd = otherd;
    }
}
