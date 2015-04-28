package com.as.controller.mvc;

import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.as.entity.User;
import com.as.services.AuthenticationService;
import com.as.services.CommonInfoService;
import com.as.services.UserCredential;
import com.as.services.UserInfoService;
import com.as.services.impl.AuthenticationServiceImpl;
import com.as.services.impl.UserInfoServiceImpl;

public class ProfileViewController extends SelectorComposer<Component> {

	// wire components
	@Wire
	Label account;
	@Wire
	Textbox fullName;
	@Wire
	Textbox email;
	@Wire
	Datebox birthday;
	@Wire
	Listbox country;
	@Wire
	Textbox bio;

	AuthenticationService authService = new AuthenticationServiceImpl();
	UserInfoService userInfoService = new UserInfoServiceImpl();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		ListModelList<String> countryModel = new ListModelList<String>(
				CommonInfoService.getCountryList());
		country.setModel(countryModel);

		refreshProfileView();
	}

	private void refreshProfileView() {

		UserCredential cre = authService.getUserCredential();
		User user = userInfoService.findUser(cre.getAccount());

		account.setValue(user.getAccount());
		fullName.setValue(user.getFullName());
		email.setValue(user.getEmail());
		birthday.setValue(user.getBirthday());
		bio.setValue(user.getBio());

		((ListModelList)country.getModel()).addToSelection(user.getCountry());
	}

	@Listen("onClick=#saveProfile")
	public void doSaveProfile() {
		UserCredential cre = authService.getUserCredential();
        User user = userInfoService.findUser(cre.getAccount());
        if(user==null){
            //TODO handle un-authenticated access 
            return;
        }
         
        //apply component value to bean
        user.setFullName(fullName.getValue());
        user.setEmail(email.getValue());
        user.setBirthday(birthday.getValue());
        user.setBio(bio.getValue());
         
        Set<String> selection = ((ListModelList)country.getModel()).getSelection();
        if(!selection.isEmpty()){
            user.setCountry(selection.iterator().next());
        }else{
            user.setCountry(null);
        }
         
        userInfoService.updateUser(user);
        
        Clients.showNotification("Your profile is updated");
	}

	@Listen("onClick=#reloadProfile")
	public void doReloadProfile() {
		refreshProfileView();
	}
}
