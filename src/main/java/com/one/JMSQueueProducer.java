package com.one;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by huangyifei on 2018/7/8.
 */
public class JMSQueueProducer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://35.185.164.128:61616");

            Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            connection.start();
            //创建session,TRUE为定义为事务型连接，AUTO_ACKNOWLEDGE属性用在Consumer端，意为自动确认信息
            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //创建目的(Queue类型)
            Destination destination = session.createQueue("MyQueue");
            //创建生产者
            MessageProducer messageProducer = session.createProducer(destination);
            //创建并发送信息
            TextMessage textMessage = session.createTextMessage("HelloWorld");
            messageProducer.send(textMessage);
            //事务型连接需要手动commit()来提交事务，不然信息不会到Borker
            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (connection !=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
