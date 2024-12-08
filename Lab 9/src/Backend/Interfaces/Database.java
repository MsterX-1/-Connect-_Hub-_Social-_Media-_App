package Backend.Interfaces;

import java.util.ArrayList;

public interface Database<T> {
    void loadFromJson( );
    void saveToJson( );
    void insertData( T data );
    void deleteData(T data);
    ArrayList<T> getData( );
    T getDataById(String id);
}
