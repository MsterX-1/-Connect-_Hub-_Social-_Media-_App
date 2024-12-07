package Backend.Databases;

import Backend.Interfaces.Database;

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

    public ArrayList<T> getData() {
        return database.getData();
    }
}
