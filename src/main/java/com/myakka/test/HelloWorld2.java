package com.myakka.test;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 * actor中传递的对象要是不可变对象。当然是为了安全了
 */
//http://blog.csdn.net/liubenlong007/article/details/54093794
 class Message {
    private  final  int age;
    private final List<String> list;

    public  Message(int age,List<String> list){
        this.age=age;
        this.list= Collections.unmodifiableList(list); //不可修改的列表
    }

    public int getAge() {
        return age;
    }

    public List<String> getList() {
        return list;
    }
}


class Greeter2 extends UntypedActor{

@Override
    public void onReceive(Object o) throws Exception {

    try {
        System.out.println("Greeter收到的数据为:"+ JSON.toJSONString(o));
        getSender().tell("Greeter工作完成.",getSelf()); //给发送至发送信息
    } catch (Exception e) {
        e.printStackTrace();
    }

}
}

public class  HelloWorld2 extends UntypedActor{
    ActorRef greeter;

    @Override
    public void preStart() throws Exception {
       greeter=getContext().actorOf(Props.create(Greeter.class),"greeter");
        System.out.println("Greeter actor path: "+greeter.path());
        greeter.tell(new Message(2, Arrays.asList("2","dsf")),getSelf());

    }

    @Override
    public void onReceive(Object msg) throws Exception {
        try {
            System.out.println("HelloWorld收到的数据为: "+JSON.toJSONString(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        akka.Main.main(new String[] { HelloWorld.class.getName() });
    }
}








