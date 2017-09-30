package com.ec.thrift.c.group;


import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import service.interfaces.c.group.IServerGroup;

public class ThriftServiceClient {

    private static IServerGroup.Client client;
    private static TTransport transport;

    public static void initClient() throws TTransportException {
        System.out.println("=========> Thrift client GetPeopleServiceClient initialing...");
        transport = new TFramedTransport(new TSocket("192.168.1.222", 11611));
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new IServerGroup.Client(protocol);
        System.out.println("=========> Thrift client GetPeopleServiceClient started...");
    }

    /**
     * 调用 GetPeople 服务
     *
     * @param args
     */
    
    public static void main(String[] args) {
    	
    	long adminId = 5024944;
    	String memberList;	//群成员信息，member[]的json数据
    	String name = "groupName1";
    	String notice = "groupNotice1";
    	int memberAdd = 0;	//0只有管理员，1任何成员
    	
    	memberList = "{\"memberList\": ["
    			+ "{\"userId\": 5024952,\"userName\": \"【开发库】user1\"},"
    			+ "{\"userId\": 5024956,\"userName\": \"user2\"}]}";
    	
        try {
            // 初始化thrift client
            initClient();

            String result = client.create(adminId, name, notice, memberAdd);
            System.out.println(result);

            // 关闭thrift client
            transport.close();

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}