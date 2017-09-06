package com.github.dhc.framework.hom.hbase;

/**
 * Created by hcdeng on 2017/9/5.
 * abstract the generation of rowkey generation
 */
public abstract class RowkeyProvider<PoType, KeyType> {

    /**
     * generate a rowkey from given po
     * @param po the given po for rowkey generation
     * @return
     */
    public abstract KeyType generateRowkey(PoType po);
}
