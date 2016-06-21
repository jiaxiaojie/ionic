package com.thinkgem.jeesite.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class MyBeanUtils extends BeanUtils
{
  @SuppressWarnings("rawtypes")
public static void copyMap2Bean(Object bean, Map properties)
    throws IllegalAccessException, InvocationTargetException
  {
    if ((bean == null) || (properties == null)) {
      return;
    }

    Iterator names = properties.entrySet().iterator();
    while (names.hasNext()) {
      Map.Entry entry = (Map.Entry)names.next();

      if (entry.getKey() != null)
      {
        Object value = entry.getValue();
        try {
          Class clazz = PropertyUtils.getPropertyType(bean, 
            (String)entry.getKey());
          if (clazz != null)
          {
            String className = clazz.getName();
            if ((!className.equalsIgnoreCase("java.sql.Timestamp")) || (
              (value != null) && (!value.equals(""))))
            {
              setProperty(bean, (String)entry.getKey(), value);
            }
          }
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
        }
      }
    }
  }

  @SuppressWarnings("rawtypes")
public static void copyMap2Bean(Object bean, Map properties, String defaultValue)
    throws IllegalAccessException, InvocationTargetException
  {
    if ((bean == null) || (properties == null)) {
      return;
    }

    Iterator names = properties.entrySet().iterator();
    while (names.hasNext()) {
      Map.Entry entry = (Map.Entry)names.next();

      if (entry.getKey() != null)
      {
        Object value = entry.getValue();
        try {
          Class clazz = PropertyUtils.getPropertyType(bean, 
            (String)entry.getKey());
          if (clazz != null)
          {
            String className = clazz.getName();
            if ((!className.equalsIgnoreCase("java.sql.Timestamp")) || (
              (value != null) && (!value.equals(""))))
            {
              if ((className.equalsIgnoreCase("java.lang.String")) && 
                (value == null)) {
                value = defaultValue;
              }

              setProperty(bean, (String)entry.getKey(), entry.getValue());
            }
          }
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
        }
      }
    }
  }

  public static void copyBean2Bean(Object dest, Object orig)
    
  {
    try {
		convert(dest, orig);
	} catch (IllegalAccessException | InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  @SuppressWarnings({ "rawtypes", "unused" })
private static void convert(Object dest, Object orig)
    throws IllegalAccessException, InvocationTargetException
  {
    if (dest == null) {
      throw new IllegalArgumentException("No destination bean specified");
    }
    if (orig == null) {
      throw new IllegalArgumentException("No origin bean specified");
    }

    if ((orig instanceof DynaBean)) {
      DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass()
        .getDynaProperties();
      for (int i = 0; i < origDescriptors.length; i++) {
        String name = origDescriptors[i].getName();
        if (PropertyUtils.isWriteable(dest, name)) {
          Object value = ((DynaBean)orig).get(name);
          try {
            copyProperty(dest, name, value);
          }
          catch (Exception localException) {
          }
        }
      }
    }
    else if ((orig instanceof Map)) {
      Iterator names = ((Map)orig).keySet().iterator();
      while (names.hasNext()) {
        String name = (String)names.next();
        if (PropertyUtils.isWriteable(dest, name)) {
          Object value = ((Map)orig).get(name);
          try {
            copyProperty(dest, name, value);
          }
          catch (Exception localException1)
          {
          }
        }
      }
    }
    else
    {
      PropertyDescriptor[] origDescriptors = 
        PropertyUtils.getPropertyDescriptors(orig);
      for (int i = 0; i < origDescriptors.length; i++) {
        String name = origDescriptors[i].getName();
        String type = origDescriptors[i].getPropertyType().toString();
        if (!"class".equals(name))
        {
          if ((PropertyUtils.isReadable(orig, name)) && 
            (PropertyUtils.isWriteable(dest, name)))
            try {
              Object value = PropertyUtils.getSimpleProperty(orig, 
                name);
              copyProperty(dest, name, value);
            }
            catch (IllegalArgumentException localIllegalArgumentException)
            {
            }
            catch (Exception localException2)
            {
            }
        }
      }
    }
  }

  public static void copyBean2Bean(Object dest, String dest_suffix, Object orig, String orig_suffix)
    throws IllegalAccessException, InvocationTargetException
  {
    convert(dest, dest_suffix, orig, orig_suffix);
  }

  @SuppressWarnings({ "rawtypes", "unused" })
private static void convert(Object dest, String dest_suffix, Object orig, String orig_suffix)
    throws IllegalAccessException, InvocationTargetException
  {
    if (dest == null) {
      throw new IllegalArgumentException("No destination bean specified");
    }
    if (orig == null) {
      throw new IllegalArgumentException("No origin bean specified");
    }

    if ((orig instanceof DynaBean)) {
      DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass()
        .getDynaProperties();
      for (int i = 0; i < origDescriptors.length; i++) {
        String name = origDescriptors[i].getName();
        String destname = "";
        if (name.endsWith(orig_suffix)) {
          destname = StringUtils.stripEnd(name, orig_suffix) + 
            dest_suffix;
        }
        if (PropertyUtils.isWriteable(dest, name)) {
          Object value = ((DynaBean)orig).get(name);
          try {
            copyProperty(dest, name, value);
          }
          catch (Exception localException) {
          }
        }
        if ((destname.length() != 0) && 
          (PropertyUtils.isWriteable(dest, destname))) {
          Object value = ((DynaBean)orig).get(name);
          try {
            copyProperty(dest, destname, value);
          } catch (Exception localException1) {
          }
        }
      }
    }
    else if ((orig instanceof Map)) {
      Iterator names = ((Map)orig).keySet().iterator();
      while (names.hasNext()) {
        String name = (String)names.next();
        String destname = "";
        if (name.endsWith(orig_suffix)) {
          destname = StringUtils.stripEnd(name, orig_suffix) + 
            dest_suffix;
        }
        if (PropertyUtils.isWriteable(dest, name)) {
          Object value = ((Map)orig).get(name);
          try {
            copyProperty(dest, name, value);
          }
          catch (Exception localException2) {
          }
        }
        if ((destname.length() != 0) && 
          (PropertyUtils.isWriteable(dest, destname))) {
          Object value = ((Map)orig).get(name);
          try {
            copyProperty(dest, destname, value);
          }
          catch (Exception localException3)
          {
          }
        }
      }
    }
    else {
      PropertyDescriptor[] origDescriptors = 
        PropertyUtils.getPropertyDescriptors(orig);
      for (int i = 0; i < origDescriptors.length; i++) {
        String name = origDescriptors[i].getName();
        String destname = "";
        if (name.endsWith(orig_suffix)) {
          destname = StringUtils.stripEnd(name, orig_suffix) + 
            dest_suffix;
        }
        String type = origDescriptors[i].getPropertyType().toString();
        if (!"class".equals(name))
        {
          if (PropertyUtils.isReadable(orig, name))
          {
            if (PropertyUtils.isWriteable(dest, name))
              try {
                Object value = PropertyUtils.getSimpleProperty(
                  orig, name);
                copyProperty(dest, name, value);
              }
              catch (IllegalArgumentException localIllegalArgumentException)
              {
              }
              catch (Exception localException4) {
              }
            if ((destname.length() != 0) && 
              (PropertyUtils.isWriteable(dest, destname)))
              try {
                Object value = PropertyUtils.getSimpleProperty(
                  orig, name);
                copyProperty(dest, destname, value);
              }
              catch (IllegalArgumentException localIllegalArgumentException1)
              {
              }
              catch (Exception localException5)
              {
              }
          }
        }
      }
    }
  }
}
