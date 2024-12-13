package Databases;

import Interfaces.Database;
import java.util.HashMap;
import java.util.Map;

public class DataManagerFactory {
    private static final Map<String, DataManager<?>> dataManagerCache = new HashMap<>();

    public static <T> DataManager<T> getDataManager(String type) {
        if (!dataManagerCache.containsKey(type.toLowerCase())) {

            // Create DataManager with the appropriate database
            Database<T> database = DatabaseFactory.createDatabase(type);
            DataManager<T> dataManager = new DataManager<>(database);
            dataManagerCache.put(type.toLowerCase(), dataManager);
        }
        return (DataManager<T>) dataManagerCache.get(type.toLowerCase());
    }
}
