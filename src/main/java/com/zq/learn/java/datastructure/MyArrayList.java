package com.zq.learn.java.datastructure;

import com.sun.glass.ui.Size;
import com.zq.learn.java.utils.JsonUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/9/30
 **/
public class MyArrayList<T> implements Iterable<T>{

    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private Object[] elems;

    public MyArrayList() {
        elems = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int initCapacity) {
        elems = new Object[initCapacity];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public boolean add(T elem) {
        ensureCapacity(size + 1);
        elems[size++] = elem;
        return true;
    }

    public void add(int index, T elem) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);

        System.arraycopy(elems,index,elems,index + 1,size - index);
//        for (int i = size - 1; i > index ; i--) {
//            elems[i + 1] = elems[i];
//        }

        elems[index] = elem;
        size++;
    }

    public T remove(int index) {
        rangeCheck(index);

        T oldElem = get(index);

        int numRemoved = size - index - 1;
        if(numRemoved > 0){
            System.arraycopy(elems, index + 1, elems, index, numRemoved);
        }

        elems[--size] = null;
        return oldElem;
    }

    public T get(int index) {
        rangeCheck(index);

        return (T) elems[index];
    }

    private void ensureCapacity(int minCapacity){
        if (minCapacity > elems.length) {
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        int oldCapacity = elems.length;
        int newCapacity = oldCapacity + oldCapacity >> 1;

        if (minCapacity - newCapacity > 0) {
            newCapacity = minCapacity;
        }

        elems = Arrays.copyOf(elems, newCapacity);
        /*Object[] oldElems = elems;
        Object[] newElems = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElems[i] = oldElems[i];
        }*/
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void rangeCheck(int index){
        if(index >= size){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator<T>();
    }

    public class MyArrayListIterator<T> implements Iterator<T> {

        private int curIndex;

        @Override
        public boolean hasNext() {
            return curIndex < size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) get(curIndex++);
        }

        @Override
        public void remove() {
            if(curIndex - 1 < 0){
                throw new NoSuchElementException();
            }
            MyArrayList.this.remove(--curIndex);
        }
    }

    public static void main(String[] args) {
//        System.out.println(13 >> 1);
        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next % 2 == 0) {
                iterator.remove();
            }
        }

        for (Integer integer : list) {
            System.out.println(integer);
        }

//        System.out.println(JsonUtils.format(list));
    }
}
