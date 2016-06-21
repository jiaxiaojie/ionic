/**
 *
 */
package com.thinkgem.jeesite.common.yeepay;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * @author yangtao
 *
 */
public class MapCustomConverter extends AbstractCollectionConverter {

	public MapCustomConverter(Mapper mapper) {
		super(mapper);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter
	 * #canConvert(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		// TODO Auto-generated method stub
		return type.equals(HashMap.class)
				|| type.equals(Hashtable.class)
				|| type.getName().equals("java.util.LinkedHashMap")
				|| type.getName().equals("sun.font.AttributeMap");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter
	 * #marshal(java.lang.Object,
	 * com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 * com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
						MarshallingContext context) {
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String,Object>) source;
		for (Iterator<?> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
			ExtendedHierarchicalStreamWriterHelper.startNode(writer,
					"property", Entry.class);

			writer.addAttribute("name", entry.getKey().toString());
			writer.addAttribute("value", entry.getValue().toString());
			writer.endNode();
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter
	 * #unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 * com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
							UnmarshallingContext context) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String,Object>) createCollection(context.getRequiredType());
		populateMap(reader, context, map);
		return map;
	}

	protected void populateMap(HierarchicalStreamReader reader,
							   UnmarshallingContext context, Map<String,Object> map) {
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String key = reader.getAttribute("name").toString();
			Object value = reader.getAttribute("value");
			map.put(key, value);
			reader.moveUp();
		}
	}

}
