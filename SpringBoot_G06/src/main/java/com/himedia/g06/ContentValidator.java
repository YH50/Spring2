package com.himedia.g06;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    } // 얘 안 씀




    @Override
    public void validate(Object target, Errors errors) {
        //Object target : 검사할 객체를 받는 매개변수. 전달된 객체 멘버변수가 비어있는지 검사할 예정
        // Errors errors : 보내온 객체에 에러내용을 담을 매개변수

/*          검사 방법 #1
        // 자료형 복원
        ContentDto dto = (ContentDto) target;
        // 전송된 값들 추출
        String sWriter = dto.getWriter();
        String sContent = dto.getContent();

        // 각 필드가 null or 비어있다면 field 에 멤버변수 이름,errorCode 에 내용 넣음.

        if(sWriter == null||sWriter.isEmpty()){
            errors.rejectValue("writer", "trouble");
        }
        if(sContent == null||sContent.isEmpty()){
            errors.rejectValue("content", "trouble");
        }
*/
        // 검사 방법 #2
        // 전달 객체의 멤버변수를 꺼내서 보지 않고 null 이거나 비어있는지를 점검. 결과는 errors 에 저장
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"writer", "writer is empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"content", "content is empty");
    }






    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }// 얘도 안 씀
}
