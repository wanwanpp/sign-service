package com.site;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class RedisTest {

//    @Mock
//    private RedisTemplate<String, Object> redisTemplate;
//
//    private CacheDao cacheDao;
//
//    @Before
//    public void setUp() throws Exception {
//        cacheDao = new CacheDao(redisTemplate, 30);
//    }
//
//    @Mock
//    private BoundHashOperations<String, Object, Object> ops;

    private Jedis jedis;

    @Before
    public void setup() {
        jedis = new Jedis("123.207.249.95", 6379);
    }

    @Test
    public void write() throws Exception {
        jedis.set("name", "wanwanpp");
        System.out.println(jedis.get("name"));
        jedis.append("name", " is me");
        System.out.println(jedis.get("name"));
        jedis.del("name");
        System.out.println(jedis.get("name"));

        //设置多个键值对
        jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
        jedis.incr("age"); //进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
    }

    /**
     * redis操作Map
     */
    @Test
    public void testMap() {
        //-----添加数据----------
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        jedis.hmset("user", map);  //HashMapSet
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);

        //删除map中的某个键值
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
    }

    /**
     * jedis操作List
     */
    @Test
    //lpush存在已有元素的左边，rpush存在已有元素的右边  ，取出是从左往右取
    public void testList() {
        //开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        //先向key java framework中存放三条数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
        //再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));
        System.out.println(jedis.llen("java framework"));
        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * jedis操作Set
     */
    @Test
    public void testSet() {
        //添加
        jedis.sadd("shima", "liuling");
        jedis.sadd("shima", "xinxin");
        jedis.sadd("shima", "ling");
        jedis.sadd("shima", "zhangxinxin");
        jedis.sadd("shima", "who");
        //移除noname
        jedis.srem("shima", "who");
        System.out.println(jedis.smembers("shima"));//获取所有加入的value
        System.out.println(jedis.sismember("shima", "who"));//判断 who 是否是user集合的元素
        System.out.println(jedis.srandmember("shima"));    //随机取一个member
        System.out.println(jedis.scard("shima"));//返回集合的元素个数
    }

    @Test
    public void test() throws InterruptedException {
        //jedis 排序
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
        jedis.del("a");//先清除数据，再加入数据进行测试
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果
        System.out.println(jedis.lrange("a", 0, -1));
    }

    @Test
    public void testRedisPool() {
        Jedis jedis = RedisUtil.getJedis();
        jedis.set("newname", "中文测试");
        System.out.println(jedis.get("newname"));
    }

    @Test
    public void testObject() throws IOException, ClassNotFoundException {

        Jedis redis = RedisUtil.getJedis();
        String set = redis.set("mingyuan", "1");
        System.out.println(" set result \t" + set);
        redis.incr("mingyuan");
        System.out.println(" set result \t" + redis.get("mingyuan"));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        Person person = new Person("wanwanpp", 19);
        oos.writeObject(person);

        byte[] byteArray = bos.toByteArray();
        oos.close();
        bos.close();

        redis.set("wanwanpp".getBytes(), byteArray);

        byte[] bytes = redis.get("wanwanpp".getBytes());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
        Person readObject = (Person) inputStream.readObject();
        System.out.println(readObject.toString());
        inputStream.close();
        byteArrayInputStream.close();

    }
}






























