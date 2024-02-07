package Framework;

public class Tapahtuma {
    int age;
    public Tapahtuma(int age){
        this.age = age;
    }
    public int compareTo(Tapahtuma tap){
        return age - tap.age;
    }
    public int getAge(){
        return age;
    }
    public long getTime(int aika){ // return total time
        return (aika - this.getAge());
    }

}
