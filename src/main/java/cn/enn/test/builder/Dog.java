package cn.enn.test.builder;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dog {

    private String name;
    private String sex;

    public Dog(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public static Dog.Builder builder(){
        return new Builder();
    }

    private static class Builder{

        private String name;
        private String sex;

        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder sex(String sex){
            this.sex = sex;
            return this;
        }

        public Dog build(){
            return new Dog(this.name, this.sex);
        }
    }

    public static void main(String[] args) {
        Dog dog = Dog.builder().name("asd").sex("asd1").build();
        log.info("=====>{}", dog);
    }

}
