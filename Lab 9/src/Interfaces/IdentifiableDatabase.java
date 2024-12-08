package Interfaces;

public interface IdentifiableDatabase<T> extends Database<T> {
    T getDataById(String id);
}
