package labproblems.repository.sortRepositories;


import labproblems.domain.entities.BaseEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author andreas.
 */
public class Sort{

    private List<String> parameters;

    public Sort(String... params){
        this.parameters = Arrays.asList(params);
    }

    public boolean contains(String string){
        return this.parameters.contains(string);
    }

    public void sort(List<BaseEntity> list){

    }
}
