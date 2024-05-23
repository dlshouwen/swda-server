package com.dlshouwen.swda.utils;

import com.dlshouwen.swda.exception.SwdaException;
import com.dlshouwen.swda.entity.base.ResultCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * validator utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ValidatorUtils {

	//	logger
	private static final Logger logger = LoggerFactory.getLogger(ValidatorUtils.class);

	/**
	 * handler
	 * @param result
	 */
	public static void handle(BindingResult result) {
//		no errors then return
		if(!result.hasErrors())
			return;
//		defined and add messages
		List<String> messages = new ArrayList<>();
		for (ObjectError error : result.getAllErrors()) 
			messages.add(error.getDefaultMessage());
//		throw exception
		try{
			throw new SwdaException(ResultCode.ERROR, new ObjectMapper().writeValueAsString(messages));
		}catch(JsonProcessingException e){
			throw new SwdaException();
		}
	}

	/** validator */
	private static Validator validator;

//	init hibernate validator
	static {
		try {
			validator = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory().getValidator();
		} catch (Exception e) {
			logger.error("error", e);
		}
	}

	/**
	 * valid
	 * @param t
	 */
	public static <T> void valid(T t) {
//		get validations
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
//		if null then return
		if(constraintViolations==null||constraintViolations.isEmpty()) {
			return;
		}
//		defined and add message
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			messages.add(constraintViolation.getMessage());
		}
//		message to json
		String content;
		try {
			content = new ObjectMapper().writeValueAsString(messages);
		} catch (JsonProcessingException e) {
			throw new SwdaException(ResultCode.ERROR, "message format error: "+messages);
		}
//		throw message info
		throw new SwdaException(ResultCode.ERROR, content);
	}

}
