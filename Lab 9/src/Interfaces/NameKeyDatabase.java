package Interfaces;

public interface NameKeyDatabase <T> extends Database<T>{
    T getDataByName(String name);
}
