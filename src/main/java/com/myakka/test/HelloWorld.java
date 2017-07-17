package com.myakka.test;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by Administrator on 2017/7/13.
 */
//http://blog.csdn.net/liubenlong007/article/details/53782971




 class HelloWorld extends UntypedActor {
    @Override
    public  void  preStart(){
            //cretae the greeter actor
        final ActorRef greeter=getContext().actorOf(Props.create(Greeter.class),"greeter");
        greeter.tell(Greeter.Msg.GREET,getSelf());
    }

    public void onReceive(Object msg) throws Exception {
           if (msg==Greeter.Msg.DONE){
                getContext().stop(getSelf());
           }else {
               unhandled(msg);
           }
    }

    public static void main(String[] args) {
        akka.Main.main(new String[]{HelloWorld.class.getName() } );
    }
}


class Greeter extends  UntypedActor{
    public void onReceive(Object msg) throws Exception {
        if (msg==Msg.GREET){
            System.out.println("Hello World");
            Thread.sleep(1000);
            getSender().tell(Msg.DONE,getSelf());
        }else {
            unhandled(msg);
        }
    }

    public static  enum Msg{
        GREET,DONE;
    }


}