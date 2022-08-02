package com.mib.pumptrial;

import cn.hutool.core.util.HashUtil;

import java.time.LocalDateTime;

public class test {


    public static void main(String[] args) {
        //Time time = new Time();
        //time.setTime(LocalDateTime.of(1970, 1, 1, 10, 0, 1, 100000000));
        //////glucose.setTime(new Date(93601000));
        //System.out.println(time);
        //String x = JSON.toJSONString(time);
        //System.out.println(x);
        //System.out.println(JSON.to(Time.class, x));
        ////
        ////System.out.println(Instant.ofEpochMilli(93601000));

        System.out.println("1sasa".hashCode());
        System.out.println(HashUtil.hfHash("1sasa"));
        System.out.println("1sasa".hashCode());


    }

    static class Time {
        private LocalDateTime time;

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "Glucose{" +
                    "time=" + time +
                    '}';
        }
    }

}
