package com.assignment.voyage.aop;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParameterAop {

    // com/assignment/voyage/controller 패키지 하위 클래스들 전부 적용하겠다고 지점 설정
    @Pointcut("execution(* com.assignment.voyage.controller..*.*(..))")
    private void parameterCheck() {}

    @Before("parameterCheck()")
    public void before (JoinPoint joinPoint) {
        // MethodSignature : 클래스의 리턴 타입과 메서드를 받아오는 객체
        // joinPoint.getSignature은 join point에서의 signature를 리턴
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 해당 메서드를 가져온다.
        Method method = methodSignature.getMethod();
        System.out.println(method.getName() + "메서드 실행");

        // 메서드에 들어가는 매개변수 배열을 읽어옴
        Object[] args = joinPoint.getArgs();

        // 매개변수 배열의 종류와 값을 출력
        // obj.getClass().getSimpleName() : 소스코드에 제공된 간단한 클래스 이름을 반환한다.
        for(Object obj : args) {
            System.out.println("type : " + obj.getClass().getSimpleName());
            System.out.println("value : " + obj);
        }
    }
}
