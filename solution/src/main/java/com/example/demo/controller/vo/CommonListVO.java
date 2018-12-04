package com.example.demo.controller.vo;

import java.util.List;

/**
 * 通用ListVO
 * @param <T>
 */
public class CommonListVO<T> extends CommonVO{

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
