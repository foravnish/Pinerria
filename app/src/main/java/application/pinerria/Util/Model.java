package application.pinerria.Util;

/**
 * Created by Andriod Avnish on 15-Mar-18.
 */

public class Model {

    public static String id;
    public static String name;

    public Model(String id, String name){
        this.id=id;
        this.name=name;
    }

    public  void setId(String id) {
        Model.id = id;
    }

    public  String getId() {
        return id;
    }

    public  void setName(String name) {
        Model.name = name;
    }

    public  String getName() {
        return name;
    }
}
