/*
package um.edu.uy.tads;

import um.edu.uy.exceptions.ElementoYaExistenteException;
import um.edu.uy.interfaces.HashTable;

import java.util.LinkedList;

public class HashTableAbiertaListaOrdenada<K,T> implements HashTable<K,T> {

    private LinkedList<HashEntry<K,T>>[] entryArray;
    private int size;

    public MyOpenedHashImpl(int size) {
        this.entryArray = new MyList[getNextPrimeNumber(size-1)];
        this.size = 0;
    }

    @Override
    public void put(K key, T value) {

        if (((float) this.size + 1) / ((float) entryArray.length) > 0.75 ){
            this.reHashing();
        }


        int position = key.hashCode() % entryArray.length;

        MyList<HashEntry<K,T>> listForPosition = entryArray[position];



        if (listForPosition == null){
            listForPosition = new MyLinkedListImpl<>();
            entryArray[position] = listForPosition;

        }

        HashEntry<K,T> hashEntry = new HashEntry<>(key, value);
        HashEntry<K,T> hashSearch = listForPosition.getValue(hashEntry);


        if (hashSearch != null) {
            hashSearch.setValue(value);
        } else{
            listForPosition.add(hashEntry);
            this.size++;
        }
    }

    @Override
    public T remove(K key)  {

        T valueToReturn = null;
        int position = key.hashCode() % entryArray.length;
        MyList<HashEntry<K,T>> listForPosition = entryArray[position];

        if (listForPosition != null) {
            HashEntry<K,T> temp = new HashEntry<>(key, null);
            HashEntry<K,T> hash = listForPosition.removeValue(temp);
            if (hash != null){
                valueToReturn = hash.getValue();
            }
            this.size--;
        }

        return valueToReturn;
    }

    @Override
    public T get(K key) {
        T valueToReturn = null;
        int position = key.hashCode() % entryArray.length;
        MyList<HashEntry<K,T>> listForPosition = entryArray[position];

        if (listForPosition != null){
            for(HashEntry<K,T> temp : listForPosition) {
                if (temp.getKey().equals(key)) {
                    valueToReturn = temp.getValue();
                    break;
                }
            }
        }

        return valueToReturn;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public MyList<T> getValues() {
        MyList<T> listToReturn = new MyLinkedListImpl<>();

        MyList<HashEntry<K,T>> listForPosition = null;
        for (int i=0; i<entryArray.length; i++){
            if (entryArray[i] != null){
                listForPosition = entryArray[i];
                for (HashEntry<K,T> hs : listForPosition){
                    listToReturn.add(hs.getValue());
                }
            }
        }

        return listToReturn;
    }

    @Override
    public T[] getValuesArray() {
        T[] vectorToReturn = (T[]) new Object[this.size];
        int position = 0;

        MyList<HashEntry<K,T>> listForPosition = null;
        for (int i=0; i<entryArray.length; i++){
            if (entryArray[i] != null){
                listForPosition = entryArray[i];
                for (HashEntry<K,T> hs : listForPosition){
                    vectorToReturn[position] = hs.getValue();
                    position++;
                }
            }
        }
        return vectorToReturn;
    }




    private void reHashing(){

        int newLength = getNextPrimeNumber(this.entryArray.length * 2);

        MyList<HashEntry<K,T>>[] oldHash = this.entryArray;
        this.entryArray = new MyList[newLength];


        int updates = 0;
        for(int i = 0; i<oldHash.length;i++){
            if (oldHash[i] != null){
                for(int j = 0; j< oldHash[i].tamanio();j++){
                    try {
                        HashEntry<K,T> valueToRehash = oldHash[i].get(j);
                        this.put(valueToRehash.getKey(), valueToRehash.getValue());
                        updates++;
                    } catch (ListOutOfIndex listOutOfIndex) {
                        listOutOfIndex.printStackTrace();
                    }
                }
            }
            if (updates >= size){
                break;
            }

        }
        this.size = updates;

    }

    private int getNextPrimeNumber(int number){
        int numberToReturn = number + 1;

        while(isNotPrime(numberToReturn)){
            numberToReturn++;
        }

        return numberToReturn;
    }

    private boolean isNotPrime(int number){

        boolean isNotPrime = false;

        for (int i=2; i<number-1; i++){
            if (number % i == 0){
                isNotPrime = true;
                break;
            }
        }

        return isNotPrime;
    }


}
*/