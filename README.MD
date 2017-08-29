## hom4j

 hom4j(hbase-object-mapping for Java)是一个轻量级的HBASE和Java对象映射框架。

### 使用方法
#### 在resource下提供hom4j.properties配置文件，主要提供hbase相关设置。常用配置：
```java
  hbase.rootdir=hdfs://tb-215:8020/home/cloudera/hbase
  zookeeper.znode.parent=/hbase
  hbase.zookeeper.quorum=tb-210,tb-205,tb-200

```

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
  HCriteria.putCriteria(Book.class).putObject(book).build().execute(homClient);
  HCriteria.putCriteria(Book.class).putList(Arrays.asList(book)).build().execute(homClient);

  //查找
  Book book = HCriteria.findCriteria(Book.class).byRowKey(TypeParsers.toBytes(1234)).build().query(homClient);

  //聚合
  long count = HCriteria.aggregateCriteria(Book.class).fromRow(TypeParsers.toBytes(1234)).toRow(TypeParsers.toBytes(1245)).build().count(homClient);

  //删除
  HCriteria.deleteCriteria(Book.class).byRowKey(TypeParsers.toBytes(1234)).build().execute(homClient);

```
