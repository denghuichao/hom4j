package com.ctrip.tourtailor.framework.hbase.criteria;

import java.util.List;

/**
 * Created by hcdeng on 17-8-25.
 */
class PutOperation<T> extends Operation<T> {

    private T po;

    private List<T> poList;

    public T getPo() {
        return po;
    }

    public void setPo(T po) {
        this.po = po;
    }

    public List<T> getPoList() {
        return poList;
    }

    public void setPoList(List<T> poList) {
        this.poList = poList;
    }
}
