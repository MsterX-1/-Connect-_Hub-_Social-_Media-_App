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

    public ArrayList<T> getAllData() {
        return database.getData();
    }

    public T getDataById(String id) {
        T data = null;
        for (int i = 0; i < database.getData().size(); i++) {
            if (id.equals(database.getData().get(i))) {
                data = database.getData().get(i);
            }
        }
        return data;
    }
}
