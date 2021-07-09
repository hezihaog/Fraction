package com.zh.android.fraction;

/**
 * Fraction的事务抽象
 */
public abstract class FractionTransaction {
    /**
     * 添加Fraction到View容器中，不设置Tag
     *
     * @param containerId 容器Id
     * @param fraction    Fraction实例
     */
    public abstract FractionTransaction add(int containerId, Fraction fraction);

    /**
     * 添加Fraction到View容器中
     *
     * @param containerId 容器Id
     * @param fraction    Fraction实例
     * @param tag         唯一Tag标志名
     */
    public abstract FractionTransaction add(int containerId, Fraction fraction, String tag);

    /**
     * 依附
     */
    public abstract FractionTransaction attach(Fraction fraction);

    /**
     * 替换Fraction
     */
    public abstract FractionTransaction replace(int containerId, Fraction fraction);

    /**
     * 替换Fraction
     */
    public abstract FractionTransaction replace(int containerId, Fraction fraction, String tag);

    /**
     * 移除Fraction
     */
    public abstract FractionTransaction remove(Fraction fraction);

    /**
     * 显示Fraction
     */
    public abstract FractionTransaction show(Fraction fraction);

    /**
     * 隐藏Fraction
     */
    public abstract FractionTransaction hide(Fraction fraction);

    /**
     * 提交事务
     */
    public abstract void commit();
}