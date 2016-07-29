package com.yimei.boot.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xiangyang on 16/4/10.
 */
public class CollectionUtils {


    /**
     * 返回a-b的新List.
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<T>(a);
        list.removeAll(b);
        return list;
    }

    /**
     * 返回a与b的交集的新List.
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();

        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
     */
    public static String convertToString(final Collection collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     */
    public static List extractToList(final Collection collection, final String propertyName) {
        return extractToList(collection, propertyName, false);
    }

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     */
    public static List extractToListExcludeNull(final Collection collection, final String propertyName) {
        return extractToList(collection, propertyName, true);
    }

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     * @param flag         true exclude    false   noExclude  是否排除值为null的元素
     */
    public static List extractToList(final Collection collection, final String propertyName, boolean flag) {
        List list = new ArrayList(collection.size());
        try {
            for (Object obj : collection) {
                Object value = PropertyUtils.getProperty(obj, propertyName);
                if (flag && value != null) {
                    list.add(value);
                } else {
                    list.add(value);
                }
            }
        } catch (Exception e) {
            throw ReflectionsUtils.convertReflectionExceptionToUnchecked(e);
        }

        return list;
    }
}
