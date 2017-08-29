package com.dhc.github.framework;

import com.dhc.github.framework.conf.HColumnDefinition;
import com.dhc.github.framework.exception.*;
import com.dhc.github.framework.pos.Book;
import com.dhc.github.framework.pos.Po1;
import com.dhc.github.framework.pos.Po2;
import com.dhc.github.framework.pos.Po3;
import com.dhc.github.framework.utils.HBaseUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by hcdeng on 17-8-28.
 */
public class ColumDefinitionTest {
    @Test
    public void testColumDefinitionTest(){
        List<HColumnDefinition> hColumnDefinitionList = HBaseUtil.getHColumnDefinitions(Book.class);
        Assert.assertEquals(hColumnDefinitionList.size(), 4);
        HColumnDefinition rowKey = HBaseUtil.getRowKey(Book.class);
        Assert.assertEquals(rowKey.getColummName(), "bookId");
    }

    @Test(expected = NotATableException.class)
    public void testColumDefinitionTestNotTable(){
        List<HColumnDefinition> hColumnDefinitionList = HBaseUtil.getHColumnDefinitions(Po1.class);
    }

    @Test(expected = RowKeyNotDefineException.class)
    public void testColumDefinitionTestNotRowKey(){
        HColumnDefinition rowKey = HBaseUtil.getRowKey(Po2.class);
    }

    @Test(expected = ColumFamilyNotDefineException.class)
    public void testColumDefinitionTestNotFamily(){
        HColumnDefinition rowKey = HBaseUtil.getRowKey(Po3.class);
    }
}
