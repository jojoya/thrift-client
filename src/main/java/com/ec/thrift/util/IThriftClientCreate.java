package com.ec.thrift.util;

import org.apache.thrift.protocol.TProtocol;

/**
 * @ClassName: IThriftClientCreate
 * @Description: thrift客户端实例化接口
 * @author longqingping
 * @date 2015年12月23日 上午11:35:29
 */
public interface IThriftClientCreate {

    public Object create(TProtocol protocol);

}
