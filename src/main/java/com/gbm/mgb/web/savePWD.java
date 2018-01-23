package com.gbm.mgb.web;

import org.junit.Test;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by 卷码生成 on 2018/1/2.
 */
@Service
public class savePWD {

    //生成随机数字和字母,
    public  String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    public  String getStringRandom2() {
        int[] array = {1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 9; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for(int i = 0; i < 6; i++){
            result = result * 10 + array[i];}
        return result+"";
    }

    @Test
    public void  tes() {
        for (int i = 0; i < 38; i++) {
            String s = getStringRandom(12).toUpperCase();
            System.out.println(s+"\t"+getStringRandom2());
        }
    }
}
