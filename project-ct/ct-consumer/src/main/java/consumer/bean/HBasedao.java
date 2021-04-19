package consumer.bean;

import common.bean.BasicDao;
import common.constant.Names;
import common.constant.ValueConstant;

/**
 * HBase数据访问对象
 */
public class HBasedao extends BasicDao {
    /**
     * 初始化，创建表
     * @throws Exception
     */
    public void init() throws Exception {
        // 获取Connection  Admin
        start();

        // 创建命名空间，NX--没有的话直接创建，有的话不再创建
        createNamespaceNX(Names.NAMESPACE.getValue());

        // 创建表,XX--有的话删除之后再创建，没有的话直接创建;这样创建的话只有一个分区
        createTableXX(Names.TABLE.getValue(), ValueConstant.REGION_COUNT,Names.CF_CALLER.getValue());

        // 释放资源
        end();
    }

    /**
     *
     */
    public void insertDatas() {
    }
}
