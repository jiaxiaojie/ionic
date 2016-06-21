package com.thinkgem.jeesite.test.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thinkgem.jeesite.modules.entity.CustomerBase;

@XmlRootElement(name="customerBaseList")
public class CustomerBaseList {
    @XmlElement(name="customerBase")
    private List<CustomerBase> customerBaseList;
 
    public CustomerBaseList(){
    	customerBaseList = new ArrayList<>();
    }
 
    public CustomerBaseList(List<CustomerBase> customerBaseList) {
    	this.customerBaseList = customerBaseList;
    }
    public void add(CustomerBase CustomerBase){
    	customerBaseList.add(CustomerBase);
    }
 
    public Iterator<CustomerBase> iterator(){
        return customerBaseList.iterator();
    }
}