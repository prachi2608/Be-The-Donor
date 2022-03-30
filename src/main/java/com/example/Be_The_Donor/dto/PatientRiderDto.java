package com.example.Be_The_Donor.dto;


import com.example.Be_The_Donor.model.NameQuantity;
import com.example.Be_The_Donor.model.PatientRiderModel;
import lombok.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@ToString
public class PatientRiderDto
{
    private String firstname;
    private String address;
    private String city;
    private String postal_code;
    private String phone_number;
    private BigInteger order_id;
    private boolean checked;
    List<NameQuantity> nameQuantities = new ArrayList<>();
    static  List<PatientRiderDto> patientRiderDtoList;


    public PatientRiderDto()
    {

    }

    public PatientRiderDto(String firstname, String address, String city, String postal_code, String phone_number, BigInteger order_id) {
        this.firstname = firstname;
        this.address = address;
        this.city = city;
        this.postal_code = postal_code;
        this.phone_number = phone_number;
        this.order_id = order_id;
    }

    public static ArrayList<PatientRiderDto> convertToDto(List<PatientRiderModel> patientRiderModelList)
    {
        HashMap<BigInteger,PatientRiderDto> patientRiderDtoHashMap = new HashMap<>();
        patientRiderDtoList = new ArrayList<>();
        for (PatientRiderModel patientRiderModel:patientRiderModelList)
        {
            if(patientRiderDtoHashMap.get(patientRiderModel.getOrder_id())==null)
            {
                PatientRiderDto patientRiderDto = new PatientRiderDto();
                patientRiderDto.setOrder_id(patientRiderModel.getOrder_id());
                patientRiderDto.setFirstname(patientRiderModel.getFirstname());
                patientRiderDto.setAddress(patientRiderModel.getAddress());
                patientRiderDto.setCity(patientRiderModel.getCity());
                patientRiderDto.setPhone_number(patientRiderModel.getPhone_number());
                patientRiderDto.setPostal_code(patientRiderModel.getPostal_code());
                NameQuantity nameQuantity = new NameQuantity();
                nameQuantity.setProductName(patientRiderModel.getProduct_name());
                nameQuantity.setProductQuantity(patientRiderModel.getQuantity());
                patientRiderDto.getNameQuantities().add(nameQuantity);
                patientRiderDtoHashMap.put(patientRiderModel.getOrder_id(),patientRiderDto);
            }
            else
            {
                PatientRiderDto patientRiderDto = patientRiderDtoHashMap.get(patientRiderModel.getOrder_id());
                NameQuantity nameQuantity = new NameQuantity();
                nameQuantity.setProductName(patientRiderModel.getProduct_name());
                nameQuantity.setProductQuantity(patientRiderModel.getQuantity());
                patientRiderDto.getNameQuantities().add(nameQuantity);
                patientRiderDtoHashMap.put(patientRiderModel.getOrder_id(),patientRiderDto);
            }

        }
        return new ArrayList<>(patientRiderDtoHashMap.values()) ;
    }


}
