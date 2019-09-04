## hom4j

### hom4j(hbase-object-mapping for Java)是一个轻量级的HBASE和Java对象映射框架。
### 背景

在开发中有个同步产品snapshot数据到hbase的需求，看了下其他项目里面相关功能的代码，发现这些项目在需要这样的功能时都会
重新写一套增删查改hbase的代码，调用最原始的hbase api，这种写法和java里面直接使用最原始的jdbc api操作数据库很像。
这种写法存在一些问题，比如针对每个po的curd操作，都需要写相应的代码构造hbase的PUT,GET,DELETE等对象非常繁琐;
针对每个po，都需要写相应的代码处理hbase查询的结果集;每个po需要手动进行hbase和object之间的映射;每次都需要处理hbase的相关异常。   


显然这种做法很不优雅。   


因此决定写一个简单的HBASE和Java对象映射框架，通过注解配置po对象，提供基本的CURD功能，并提供方便的api，用户无需要关心hbase的存储细节
，只需要关注自己的领域模型和(PO)和业务逻辑。


### 使用方法
#### 在resource下提供hom4j.properties配置文件，主要提供hbase相关设置。常用配置：
```java
    zookeeper.znode.parent=/hbase_online_new
    hbase.zookeeper.quorum=10.***.***.19,10.***.***.18,10.***.***.8
```

#### 采用注解的方式定义Po和HTable的映射关系：
```java
    @Table("book") //table名可选，不指定的话和类同名
    public class Book implements Serializable{
    
        @RowKey(auto = true) // auto = true支持rowkey自动生成，必须指定一个字段为rowkey，否则异常。
        private int bookId;
    
        //每个column的family必须指定，否则异常；name可选，不指定的话和field同名
        @Column(family = "cf", name = "bookName") 
        private String bookName;
    
        @Column(family = "cf", name = "author")
        private String author;
    
        @Column(family = "cf", name = "desc")
        private String desc;
    
        ...
    }
```

#### 关于```RowKey```
- 必须要有一个字段注解为```RowKey```,否则会异常
- 为了方便自动生成, ```RowKey```字段的类型必须为```int.class, Integer.class,long.class, Long.class, BigInteger.class, String.class```其中一种，否则异常
- 可以自己设置```Rowkey```， 不推荐
- 自动生成```RowKey``` ```@RowKey(auto = true)```， 不指定```RowKeyProvider```的话，会根据系统时间自动生成
- 自定义```RowKey```自动生成策略 ```provider = {SnapshotRowkeyGenrator.class}```, 其中```SnapshotRowkeyGenrator```必须继承```RowkeyProvider```
- 推荐使用第三种方式
- [RowKey设计原则参考](http://dxer.github.io/2016/06/16/hbase_rowkey/)

#### 直接采用HomClient操作：
```java
  homClient = new HomClient();
  homClient.setHDataSource(new HDataSource(HDataSourceConfig.getConfiguration()));
  ...
```

#### 使用Criteria流式API操作
```java
  //插入
  Book book = new Book();
  book.setBookId(1234);
  book.setBookName("Thinking in java");
  book.setAuthor("hcdeng");
  book.setDesc("good book about java");
   HomCriteria.putCriteria(Book.class).putObject(book).build().execute(homClient);
   HomCriteria.putCriteria(Book.class).putList(Arrays.asList(book)).build().execute(homClient);

  //查找
  Book book =  HomCriteria.findCriteria(Book.class).byRowKey(TypeParsers.toBytes(1234)).build().query(homClient);

  //聚合
  long count =  HomCriteria.aggregateCriteria(Book.class).fromRow(TypeParsers.toBytes(1234)).toRow(TypeParsers.toBytes(1245)).build().count(homClient);

  //删除
   HomCriteria.deleteCriteria(Book.class).byRowKey(TypeParsers.toBytes(1234)).build().execute(homClient);

```

#### 更多细节
- 详细用法请参考Test Case
- 源码底下无秘密

## May you enjoy using **HOM4J** ~~

