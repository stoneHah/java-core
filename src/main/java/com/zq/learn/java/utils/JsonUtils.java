package com.zq.learn.java.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
	public static ObjectMapper mapper;
	
	static{
		mapper = new ObjectMapper();
//		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
//		mapper.getSerializationConfig().withFeatures(Feature.)
//		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		mapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
	}
	
	public static <T> T parseFrom(String str,Class<T> type) throws Exception{
		return mapper.readValue(str, type);
	}

	public static String format(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static <T> List<T> mapJsonToObjectList(T typeDef, String json, Class clazz) throws Exception {
		List<T> list;
		TypeFactory t = TypeFactory.defaultInstance();
		list = mapper.readValue(json, t.constructCollectionType(ArrayList.class, clazz));
		return list;
	}
}
