package Databases;

import Interfaces.Database;
import Interfaces.IdentifiableDatabase;
import Interfaces.NameKeyDatabase;

import java.util.ArrayList;

public class DataManager<T> {
    private final Database<T> database;

    public DataManager(Database<T> database) {
        this.database = database;
    }

    public void loadData() {
        database.loadFromJson();
    }

    public void saveData() {
        database.saveToJson();
    }

    public void insertData(T data) {
        database.insertData(data);
    }

    public void removeData(T data) {
        database.deleteData(data);
    }

    public ArrayList<T> getAllData() {
        return database.getData();
    }

    public T getDataById(String id) {
       return ( (IdentifiableDatabase<T> ) database).getDataById(id);
    }
    public T getDataByName(String name) {
        return ( (NameKeyDatabase<T>) database).getDataByName(name);
    }
}
