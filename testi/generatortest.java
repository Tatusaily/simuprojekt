package testi;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Generator;
import eduni.distributions.Normal;

public class generatortest {
    public static void main(String[] args) {
        ContinuousGenerator testigen = new Normal(10, 25);
        for (int i = 0; i < 1000; i++) {
            double num = testigen.sample();
            System.out.println(num);
        }
    }

}
