package org.example;

import java.util.List;
import java.util.NoSuchElementException;


public class AnimalIterator<T> implements BaseIterator<T>{
    private List<T> animals;
    private int index;

    AnimalIterator(List<T> animals){
        this.animals = animals;
    }

    @Override
    public T next(){
        if(hasNext()){
            return animals.get(index++);
        }
        throw new NoSuchElementException("No such element");
    }

    @Override
    public boolean hasNext() {
        if(index < animals.size()){
            return true;
        }
        return false;
    }

    @Override
    public void reset(){
        index = 0;
    }
}
