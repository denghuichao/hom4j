package com.github.dhc.framework.hom.hbase.criteria;

/**
 * Created by hcdeng on 17-8-25.
 */
abstract class Operation<T> {

    private Class<T> poType;

    public void setPoType(Class<T> poType) {
        this.poType = poType;
    }

    public  Class<T> getPoType(){
        return poType;
    }
}