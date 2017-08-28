package com.dhc.github.framework.pos;

import com.dhc.github.framework.annotation.Column;
import com.dhc.github.framework.annotation.RowKey;
import com.dhc.github.framework.annotation.Table;

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
