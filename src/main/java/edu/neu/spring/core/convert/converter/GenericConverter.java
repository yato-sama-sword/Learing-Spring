package edu.neu.spring.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Set;

/**
 * 通用的转换接口
 * @author yato
 */
public interface GenericConverter {

    /**
     * 获取所有的转换类型
     * @return 返回Source到Target的映射集合
     */
    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 转换！！！
     * @param source 资源
     * @param sourceType 资源类型
     * @param targetType 目标类型
     * @return 转换后资源
     */
    Object convert(Object source, Class sourceType, Class targetType);

    /**
     * 资源类型到目标类型的映射
     */
    final class ConvertiblePair {
        private final Class<?> sourceType;

        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType, "Source type must not be null");
            Assert.notNull(targetType, "Target type must not be null");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return this.sourceType;
        }

        public Class<?> getTargetType() {
            return this.targetType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != ConvertiblePair.class) {
                return false;
            }
            ConvertiblePair other = (ConvertiblePair) obj;
            return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);

        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }

}
