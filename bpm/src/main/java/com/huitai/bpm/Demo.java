package com.huitai.bpm;

import com.huitai.bpm.annotaion.FlwAnnotaion;
import com.huitai.bpm.manage.entity.FlwBaseEntity;

/**
 * description: Demo <br>
 * date: 2020/4/28 11:32 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
@FlwAnnotaion("Demo演示")
public class Demo extends FlwBaseEntity {

    static class P {
        public void fun(){
            System.out.println(this.getClass().getSimpleName());
        }
    }

    static class C extends P {
    }

    public static void main(String[] args) {
        P p = new C();
        p.fun();
    }
}
