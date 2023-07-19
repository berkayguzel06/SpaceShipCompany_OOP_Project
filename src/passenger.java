
public class passenger {
    String name,surname,nation,gender,age;
    public passenger(String name,String surname,String nation,String sex,String age){
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.gender = sex;
        this.nation = nation;
    }
    public String getAge() {
        return age;
    }
    public String getName() {
        return name;
    }
    public String getNation() {
        return nation;
    }
    public String getGender() {
        return gender;
    }
    public String getSurname() {
        return surname;
    }
    public String getPassenger(){
        return name+" "+surname+", "+gender+", "+age+", "+nation;
    }
}
