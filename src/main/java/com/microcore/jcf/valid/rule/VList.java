package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.rule.base.IValid;
import com.microcore.jcf.valid.rule.support.BoundaryValueSupport;
import com.microcore.jcf.valid.validate.BeanValidateAcceptor;
import com.microcore.jcf.valid.validate.util.ValidUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * List校验
 *
 * @author leizhenyang
 */
public class VList extends AbstractRule<List> {
    /**
     * 目标集合
     */
    private List<IValid> genericValidators;

    private BoundaryValueSupport boundaryValueSupport;

    public VList(IValid... genericValidArray) {
        initBoundaryValueSupport();
        this.genericValidators = Arrays.asList(genericValidArray);
    }

    public VList(long minSize, IValid... genericValidArray) {
        initBoundaryValueSupport(minSize);
        this.genericValidators = Arrays.asList(genericValidArray);
    }

    public VList(long minSize, long maxSize, IValid... genericValidArray) {
        initBoundaryValueSupport(minSize, maxSize);
        this.genericValidators = Arrays.asList(genericValidArray);
    }

    private void initBoundaryValueSupport() {
        boundaryValueSupport = new BoundaryValueSupport();
    }

    /**
     * 初始化最大长度
     *
     * @param minSize
     */
    private void initBoundaryValueSupport(long minSize) {
        boundaryValueSupport = new BoundaryValueSupport(minSize, Long.MAX_VALUE);


    }

    /**
     * 初始化最小长度、最大长度
     *
     * @param minSize
     * @param maxSize
     */
    private void initBoundaryValueSupport(long minSize, long maxSize) {
        boundaryValueSupport = new BoundaryValueSupport(maxSize);
        boundaryValueSupport.ltZero(minSize);
    }

    @Override
    public boolean valid(List list) throws Exception {
        if (ValidUtil.isNull(list) || list.isEmpty()) {
            return true;
        }
        if (!ValidUtil.between(boundaryValueSupport.getMin(), boundaryValueSupport.getMax(), list.size())) {
            message = "元素数量必须在[" + boundaryValueSupport.getMin() + "," + boundaryValueSupport.getMax() + "]之间";
            return false;
        }
        if (genericValidators != null) {
            genericValidators.sort(Comparator.comparingInt(o -> o.getOrderNumber()));
        }
        BeanValidateAcceptor acceptor = new BeanValidateAcceptor<>();
        boolean success = true;
        for (int i = 0; i < list.size(); i++) {
            Object value = list.get(i);
            Object msgTemp = null;
            if (genericValidators == null || genericValidators.isEmpty()) {
                message = "泛型验证器为空，无法校验当前List泛型实体，当前泛型为：" + value.getClass();
                return false;
            }
            if (genericValidators.size() == 1) {
                boolean valid = genericValidators.get(0).valid(value);
                if (!valid) {
                    msgTemp = genericValidators.get(0).getMessage();
                    success = false;
                }
            }
            if (genericValidators.size() > 1) {
                msgTemp = new ArrayList();
                for (IValid iValid : genericValidators) {
                    boolean valid = iValid.valid(value);
                    if (!valid) {
                        ((List) msgTemp).add(iValid.getMessage());
                        success = false;
                    }
                }
            }
            if (!ValidUtil.isNull(msgTemp)) {
                if (msgTemp instanceof List && ((List) msgTemp).isEmpty()) {
                    continue;
                }
                acceptor.put(i, msgTemp);
            }
        }
        message = acceptor;
        return success;
    }

    public List<IValid> getGenericValidators() {
        return genericValidators;
    }

    public void setGenericValidators(List<IValid> genericValidators) {
        this.genericValidators = genericValidators;
    }

    public BoundaryValueSupport getBoundaryValueSupport() {
        return boundaryValueSupport;
    }

    public void setBoundaryValueSupport(BoundaryValueSupport boundaryValueSupport) {
        this.boundaryValueSupport = boundaryValueSupport;
    }

}
