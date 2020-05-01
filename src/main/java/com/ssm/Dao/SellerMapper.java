package com.ssm.Dao;

import com.ssm.Pojo.Seller;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerMapper {
    Seller getSeller(int sid);
}
