package utils;

import java.sql.ResultSet;


/**
 * Created by Administrator on 2017/5/24.
 */
public interface ResultSetHandler<T>  {

    public T handler(ResultSet resultSet) throws Exception;



}
