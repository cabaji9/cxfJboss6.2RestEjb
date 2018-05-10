package personal.mock.base;

import personal.mock.base.entity.FileData;

import java.util.HashMap;

/**
 * Created by Hyun Woo Son on 5/10/18
 **/
public class Base {

    private static Base base;
    private static HashMap<Integer,FileData> fileDataHashMap;
    private static Integer id;

    private Base(){
        fileDataHashMap = new HashMap<>();
        id = 0;
    }

    public static Base getInstance(){
        if(base == null){
            base = new Base();
        }
        return base;
    }

    public void put(Integer id, FileData fileData){
        fileDataHashMap.put(id,fileData);
    }

    public FileData get(Integer id){
        return fileDataHashMap.get(id);
    }


    public Integer getId(){
        id = id+1;
        return id;
    }










}
