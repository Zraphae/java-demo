package cn.enn.test.nio.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MethodParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2673015142111972633L;
	
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] args;
}
