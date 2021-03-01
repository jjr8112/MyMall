1.UmsRoleServiceImpl中有一个getMenuList方法，功能是根据管理员ID获取对应菜单，
这个方法被用户管理的getAdminInfo(获取当前登录用户信息)调用。


2.Principal类

3.集合工具类CollUtil

4.Java8： stream流编程
roleList.stream().map(UmsRole::getName).collect(Collectors.toList());

特性：
  
  无存储。stream不是一种数据结构，它只是某种数据源的一个视图，数据源可以是一个数组，Java容器或I/O channel等。
  
  为函数式编程而生。对stream的任何修改都不会修改背后的数据源，比如对stream执行过滤操作并不会删除被过滤的元素，而是会产生一个不包含被过滤元素的新stream。
  
  惰式执行。stream上的操作并不会立即执行，只有等到用户真正需要结果的时候才会执行。
  
  可消费性。stream只能被“消费”一次，一旦遍历过就会失效，就像容器的迭代器那样，想要再次遍历必须重新生成。

例如：

    List<User> UserList = userMapper.selectAllUser();
    List<String> userIdList = UserList.stream().map(User::getUserId).collect(Collectors.toList());

就等价于

    List<User> UserList = userMapper.selectAllUser();
    List<String> userIdList = new ArrayList<>();
    for(User user : UserList){
    	userIdList.add(user.getUserId());
    }

5.忘了一个基本点(人傻了)：
    
    首先明确，mysql数据库增删改查有返回值。
    
    增加返回主键id，查当然返回的是你查询的数据，
    
    删除和修改都是Int 一般>0 表示成功。
    
    增删改返回int，select返回查询结果集

6.lambda表达式

7.example使用

