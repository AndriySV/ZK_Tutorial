package com.as.viewmodel;

import java.io.Serializable;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;

import com.as.entity.User;
import com.as.services.AuthenticationService;
import com.as.services.CommonInfoService;
import com.as.services.UserCredential;
import com.as.services.UserInfoService;
import com.as.services.impl.AuthenticationServiceImpl;
import com.as.services.impl.UserInfoServiceImpl;

public class ProfileViewModel implements Serializable {

	AuthenticationService authService = new AuthenticationServiceImpl();
	UserInfoService userInfoService = new UserInfoServiceImpl();

	//data for the view
    User currentUser;
    
    public User getCurrentUser(){
        return currentUser;
    }
    
    public List<String> getCountryList(){
        return CommonInfoService.getCountryList();
    }
    
    @Init // @Init annotates a initial method
    public void init(){
        UserCredential cre = authService.getUserCredential();
        currentUser = userInfoService.findUser(cre.getAccount());
        if(currentUser==null){
            //TODO handle un-authenticated access 
            return;
        }
    }
    
    @Command
    @NotifyChange("currentUser")
    public void save() {
    	currentUser = userInfoService.updateUser(currentUser);
        Clients.showNotification("Your profile is updated");
	}
    
    @Command
    @NotifyChange("currentUser")
    public void reload(){
        UserCredential cre = authService.getUserCredential();
        currentUser = userInfoService.findUser(cre.getAccount());
    }
	
}
