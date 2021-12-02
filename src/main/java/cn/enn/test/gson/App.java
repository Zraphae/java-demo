package cn.enn.test.gson;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class App {

    public static void main(String[] args){

        String jsonStr = "[{\"mBarcode\":\"123\",\"mName\":\"Apfel\",\"mPrice\":\"2.7\"},\n" +
                "{\"mBarcode\":\"456\",\"mName\":\"Pfirsich\",\"mPrice\":\"1.1111\"},\n" +
                "{\"mBarcode\":\"89325982\",\"mName\":\"Birne\",\"mPrice\":\"1.5555\"}] ";


//        Gson gson = new Gson();
//
//        ArrayList<Product> arrayList = gson.fromJson(jsonStr, ArrayList.class);
//
//        log.info("========>{}", arrayList.get(0).getMBarcode());

        List<Product> products = stringToArray(jsonStr, Product[].class);

        products.add(new Product());
        log.info("========>{}", products.get(0).getMBarcode());


//        List<Product> products = stringToList(jsonStr, List<Product>.class);
//        log.info("========>{}", products.get(0).getMBarcode());



    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Lists.newArrayList(arr);
    }

    public static <T> List<T> stringToList(String s, Class<List<T>> clazz) {
        List<T> ts = new Gson().fromJson(s, clazz);
        return ts;
    }




}


@Data
class Product {
    private String mBarcode;
    private String mName;
    private String mPrice;
}