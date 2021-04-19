package common.bean;

import common.constant.Names;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据访问对象
 * 不能直接用，要通过子类继承它来用
 */
public abstract class BasicDao {
    /**
     * 使用ThreadLocal线程设置本地变量值，可以用来对某个对象创建之后进行存储，而不需要重复创建
     */
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();
    private ThreadLocal<Admin> adminThreadLocal = new ThreadLocal<Admin>();

    protected void start() throws Exception{
        getConnection();
        getAdmin();
    }

    protected void end() throws Exception{
        Admin admin = adminThreadLocal.get();
        if (admin!=null){
            admin.close();
            adminThreadLocal.remove();
        }

        Connection conn = connectionThreadLocal.get();
        if (conn!=null){
            conn.close();
            connectionThreadLocal.remove();
        }
    }

    /**
     * 创建表，XX代表--如果已存在那么删除之后再创建新的。
     */
    protected void createTableXX(String name,String... families) throws Exception{
        createTableXX(name,null,families);
    }
    protected void createTableXX(String name,Integer ragionCount,String... families) throws Exception{
        Admin admin = getAdmin();
        TableName tableName = TableName.valueOf(name);
        if (admin.tableExists(tableName)){ // 如果存在表，先将表删除
            deleteTable(name);
        }
        createTable(name,ragionCount,families);
    }

    /**
     * 创建表
     * 因为这个方法只给createTableXX使用所以这里直接设置成为private属性
     * String... 可变长度的参数列表
     * @param name
     * @param families
     */
    private void createTable(String name,Integer regionCount,String... families) throws Exception{
        Admin admin = getAdmin();
        // 检验列族是否为空，若为空自动添加一个列族，否则直接使用。
        if (families==null || families.length==0){
            families = new String[1];
            families[0] = Names.CF_INFO.getValue();
        }

        TableName tableName = TableName.valueOf(name);

        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);

        for (String family : families) {
            // 添加列族
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
            hTableDescriptor.addFamily(hColumnDescriptor);
        }

        // 是否要增加预分区
        if (regionCount==null || regionCount<=1){ // 表示不需要进行预分区
            admin.createTable(hTableDescriptor);
        }else{ // 进行预分区
            // 设置分区键
            byte[][] splitKeys = genSplitKeys(regionCount);
            // 创建表
            admin.createTable(hTableDescriptor,splitKeys);
        }
    }

    /**
     * 设置分区键
     * @param regionCount
     * @return
     */
    private byte[][] genSplitKeys(int regionCount){
        // 因为有regionCount个分区数，所以要有regionCount-1个分区键
        int splitKeyCount = regionCount - 1;
        byte[][] bs = new byte[splitKeyCount][];
        List<byte[]> bsList = new ArrayList<byte[]>();
        for (int i = 0;i < splitKeyCount;i++){
            // 分区时，怎样判断数据该放到哪个分区，怎样确定进行分区的分区键
            // 在分区时例如对(-∞,+∞)分成3个区，可能是(-∞,0),[0,1),[1,+∞)
            // 但是只是使用0,1来进行切分的话，如果是字符串的话，(-∞,0)这个分区可能不会放数据
            // 所以使用0|,1|来分区，分成(-∞,0|),[0|,1|),[1|,+∞)
            // 因为|的ascii码只会比}的小，所以这里可以当成是分割符来使用。
            // 所以第一位等于0,但是第二位比|的ascii要小的数据，就会被放到(-∞,0)这个分区内
            String splitKey = i + "|";
            bsList.add(Bytes.toBytes(splitKey));
        }
        return bsList.toArray(bs);
    }

    /**
     * 通用性hbase删除表
     * @param name
     * @throws Exception
     */
    protected void deleteTable(String name) throws Exception{
        Admin admin = getAdmin();
        TableName tableName = TableName.valueOf(name);
        admin.disableTable(tableName); // 先禁用表
        admin.deleteTable(tableName); // 然后再删除表
    }
    /**
     * 创建命名空间，NX代表--如果已存在就不用创建，否则创建新的
     * @param namespace
     * @throws Exception
     */
    protected void createNamespaceNX(String namespace) throws Exception {
        Admin admin = getAdmin();
        // 捕捉NamespaceNotFoundException，来查看命名空间是否已经创建,没有创建的话会有NamespaceNotFoundException出现
        try {
            // 有命名空间的话就不用重新创建
            NamespaceDescriptor namespaceDescriptor = admin.getNamespaceDescriptor(namespace);
        } catch(NamespaceNotFoundException e) {
            // NamespaceDescriptor中的构造函数是private属性的，所以不能使用new
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(namespaceDescriptor);
        }

    }

    /**
     * 使用protected目的是只让子类获取连接进行使用
     * 使用synchronized来进行同步
     * 获取admin
     */
    protected synchronized Admin getAdmin() throws Exception{
        Admin admin = adminThreadLocal.get();
        if (admin==null){
            admin = getConnection().getAdmin();
            adminThreadLocal.set(admin);
        }
        return admin;
    }
    /**
     * 获取连接
     */
    protected synchronized Connection getConnection() throws Exception{
        Connection conn = connectionThreadLocal.get();
        if (conn==null){
            Configuration configuration = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(configuration);
            connectionThreadLocal.set(conn);
        }
        return conn;
    }
}
