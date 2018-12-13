package com.example.hashmap;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class CustomHashMapTest {

    CustomHashMap hashMap;


    @Before
    public void init() {
        hashMap = new CustomHashMap(16, 0.75f);
    }

    // constructor

    @Test(expected = IllegalArgumentException.class)
    public void whenInitialCapacityIsNegative_ThenIllegalArgumentException(){

        new CustomHashMap(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInitialCapacityMoreThanMaxCapacity_ThenIllegalArgumentException(){

        new CustomHashMap(Integer.MAX_VALUE-4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoadFactorIsNegative_ThenIllegalArgumentException(){
        new CustomHashMap(36, -0.5F);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLoadFactorMoreThanOne_ThenIllegalArgumentException(){
        new CustomHashMap(36, 1.5F);
    }

    // size

    @Test
    public void whenPutOneElem_ThenSizeIsOne(){
        hashMap.put(100, 10000);
        int size = hashMap.size();
        assertEquals(1, size);
    }

    @Test
    public void whenPutThreeElem_ThenSizeIsThree(){
        hashMap.put(0, 454645);
        hashMap.put(1, 158456);
        hashMap.put(2, 324093);
        int size = hashMap.size();
        assertEquals(3, size);
    }

    @Test
    public void whenPutTwoElemWithSameKeys_ThenSizeIsOne(){
        hashMap.put(55, 158456);
        hashMap.put(55, 324093);
        int size = hashMap.size();
        assertEquals(1, size);
    }

    // get & put

    @Test
    public void whenGetElem_ThenEqualsElemWasPut(){
        hashMap.put(5345, 453409580);
        long value = hashMap.get(5345);
        assertEquals(453409580, value);
    }

    @Test
    public void whenPutTwoElemWithSameKeys_ThenGetReturnsSecond(){
        hashMap.put(-458, 867451);
        hashMap.put(-458, -7586576);
        long value = hashMap.get(-458);
        assertEquals(-7586576, value);
    }


    @Test
    public void whenPutSeventeenElems_ThenResizeWorks(){

        int key;
        long value;
        Random random = new Random();

        for(int i = 0; i < 17; i++){
            key = random.nextInt();
            value = random.nextLong();
            hashMap.put(key, value);
        }

        int size = hashMap.size();
        assertEquals(17, size);
    }

    @Test
    public void whenPutIntegerMINAsKey_ThenOk(){

        hashMap.put(Integer.MIN_VALUE, Long.MIN_VALUE);
        long value = hashMap.get(Integer.MIN_VALUE);
        assertEquals(Long.MIN_VALUE, value);
    }

    @Test
    public void whenPutIntegerMAXAsKey_ThenOk(){
        hashMap.put(Integer.MAX_VALUE, Long.MAX_VALUE);
        long value = hashMap.get(Integer.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, value);
    }


    @Test(expected = NoSuchElementException.class)
    public void whenNoSuchKey_ThenIllegalArgumentException() {
        hashMap.put(123, 657562);
        hashMap.get(1);
    }
}
