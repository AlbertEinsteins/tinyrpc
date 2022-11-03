package com.test;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.aop.aspects.Aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    interface Animal {
        void eat();
    }
    static class Cat implements Animal {
        @Override
        public void eat() {
            System.out.println("The cat eat");
        }
    }


    public static void main(String[] args) {
        Cat a = new Cat();

//        Animal subClass = ProxyUtil.newProxyInstance(new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println("before eat");
//                method.invoke(a, args);
//                System.out.println("after eat");
//                return null;
//            }
//        }, Animal.class);
//        subClass.eat();

//        Aspect myAspect = new Aspect() {
//            @Override
//            public boolean before(Object target, Method method, Object[] args) {
//                System.out.println("before");
//                return true;
//            }
//
//            @Override
//            public boolean after(Object target, Method method, Object[] args, Object returnVal) {
//                System.out.println("after : res: " + returnVal);
//                return true;
//            }
//
//            @Override
//            public boolean afterException(Object target, Method method, Object[] args, Throwable e) {
//                System.out.println(e.getMessage());
//                return false;
//            }
//        };
//        Animal a2 = ProxyUtil.proxy(a, myAspect.getClass());
//        a2.eat();

    }
}
