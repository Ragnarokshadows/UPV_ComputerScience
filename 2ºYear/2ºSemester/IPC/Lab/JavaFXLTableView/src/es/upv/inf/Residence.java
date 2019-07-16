/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.inf;

/**
 *
 * @author fjabad
 */
public class Residence {
    private final String city;
    private final String province;

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }
    
    public Residence(String city, String province) {
        this.city = city;
        this.province = province;
    }
}
