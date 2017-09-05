package com.github.dhc.framework.hom.pos;

import com.github.dhc.framework.hom.annotation.Column;
import com.github.dhc.framework.hom.annotation.RowKey;
import com.github.dhc.framework.hom.annotation.Table;

/**
 * Created by hcdeng on 17-8-28.
 */
@Table("po3")
public class Po3 {
    @RowKey
    private int a;

    @Column
    private String b;
}
