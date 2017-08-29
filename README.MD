## hom4j

### hom4j(hbase-object-mapping for Java)是一个轻量级的HBASE和Java对象映射框架。
### 背景

在开发中有个同步产品快照数据到hbase的需求，看了下其他项目里面相关功能的代码，发现这些项目在需要这样的功能时都会
重新写一套增删查改hbase的代码，调用最原始的hbase api，这种写法和java里面直接使用最原始的jdbc api操作数据库很像。
这种写法存在一些问题，比如针对每个po的curd操作，都需要写相应的代码构造hbase的PUT,GET,DELETE等对象非常繁琐;
针对每个po，都需要写相应的代码处理hbase查询的结果集;每个po需要手动进行hbase和object之间的映射;每次都需要处理hbase的相关异常。

因此决定写一个简单的HBASE和Java对象映射框架，通过注解配置po对象，提供基本的CURD功能，并提供方便的api，用户无需要关心hbase的存储细节
，只需要关注自己的领域模型和(PO)和业务逻辑。


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
