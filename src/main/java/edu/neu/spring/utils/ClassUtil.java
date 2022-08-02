package edu.neu.spring.utils;

/**
 * 创建ClassLoader
 * @author yato
 */
public class ClassUtil {

    /**
     * 私有构造器
     */
    private ClassUtil() {

    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (cl == null) {
            cl = ClassUtil.class.getClassLoader();
        }
        return cl;
    }

    /**
     * @param clazz clazz
     * @return 返回是否是cglib代理类
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    /**
     * @param className 代理类名称
     * @return 返回是否存在名为className的cglib代理类
     */
    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }
}
