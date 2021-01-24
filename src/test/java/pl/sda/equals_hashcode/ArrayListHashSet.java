package pl.sda.equals_hashcode;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayListHashSet {

    @Test
    void arrayListHasSet(){
        List<Integer> list1 = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 1_000_000; i++) {
            list1.add(i);
            set.add(i);
        }
        list1.add(1_000_000);
        List<Integer> result = new ArrayList<>();
        for (Integer integer : list1) {
            if(!set.contains(integer)){
                result.add(integer);
            }
        }
        System.out.println(result);
    }

    @Test
    @Disabled
    void arrayListArrayList(){
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            list1.add(i);
            list2.add(i);
        }
        list1.add(1_000_000);
        List<Integer> result = new ArrayList<>();
        for (Integer integer : list1) {
            if(!list2.contains(integer)){
                result.add(integer);
            }
        }
        System.out.println(result);
    }

    @Test
    void arrayListHasSetPlanes(){
        List<Plane> list1 = new ArrayList<>();
        Set<Plane> set = new HashSet<>();
        for (int i = 0; i < 1_000_000; i++) {
            list1.add(new Plane("a", i, i));
            set.add(new Plane("a", i, i));
        }
        list1.add(new Plane("aaa", 1_000_000, 1_000_000));
        List<Plane> result = new ArrayList<>();
        for (Plane p : list1) {
            if(!set.contains(p)){
                result.add(p);
            }
        }
        System.out.println(result);
    }

}
