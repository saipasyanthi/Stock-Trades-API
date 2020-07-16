package com.hackerrank.stocktrade.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hackerrank.stocktrade.util.CustomDateAndTimeDeserialize;


@Entity
@Table(name = "Trade")
public class Trade  implements Serializable  {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
    private Long id;
	
    private String type;
    
   
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

	private String symbol;
    private Integer shares;
    
    private Float price;
   
 	 	
 	@JsonDeserialize(using=CustomDateAndTimeDeserialize.class)
 	@Temporal(value=TemporalType.TIMESTAMP) 
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 	  private java.util.Date timestamp;
 	 public java.util.Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}

	

    public Trade() {
    }

    public Trade(Long id, String type, User user, String symbol, Integer shares, Float stockPrice, java.util.Date tradeTimestamp) {
        this.id = id;
        this.type = type;
        this.user = user;
        this.symbol = symbol;
        this.shares = shares;
        this.price = price;
        this.timestamp = timestamp;
    }
    
   


    public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
    
}
