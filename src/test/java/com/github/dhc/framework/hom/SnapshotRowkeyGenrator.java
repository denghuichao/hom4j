package com.github.dhc.framework.hom;

import com.github.dhc.framework.hom.hbase.RowkeyProvider;
import com.github.dhc.framework.hom.pos.SchemeSnapshot;

/**
 * Created by hcdeng on 2017/9/5.
 */
public class SnapshotRowkeyGenrator extends RowkeyProvider<SchemeSnapshot, Long> {
    @Override
    public Long generateRowkey(SchemeSnapshot po) {
        //
        return po.getOrderID()&System.currentTimeMillis();
    }
}
