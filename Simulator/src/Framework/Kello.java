package Framework;

public class Kello {
    // Kello on singleton
        private static Kello instance = null;
        private int time;
        private Kello(){    // private constructor
        }
       public static Kello getInstance(){ // singleton checker
            if (instance == null){
                instance = new Kello();
            }
            return instance;
        }
        public int getTime(){
            return time;
        }
        public void setTime(int num){
            time = num;
        }
    }